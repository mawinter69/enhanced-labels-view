package io.jenkins.plugins.enhanced_labels_view.labels;

import hudson.Extension;
import hudson.model.Action;
import hudson.model.InvisibleAction;
import hudson.model.Label;
import hudson.model.labels.LabelAtom;
import hudson.model.labels.LabelAtomProperty;
import hudson.slaves.OfflineCause;
import hudson.slaves.SimpleScheduledRetentionStrategy;
import io.jenkins.plugins.enhanced_labels_view.agent_columns.AbstractAgentColumn;
import io.jenkins.plugins.enhanced_labels_view.agent_columns.WrappedNodeMonitorColumn;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import jenkins.model.IComputer;
import jenkins.model.TransientActionFactory;
import org.jspecify.annotations.NonNull;
import org.kohsuke.accmod.restrictions.suppressions.SuppressRestrictedWarnings;
import org.kohsuke.stapler.DataBoundConstructor;

@SuppressRestrictedWarnings(Label.class)
public class AgentDetailsLabelAction extends InvisibleAction {

    private final Label label;

    private static final List<String> defaultColumns = List.of(
            "io.jenkins.plugins.enhanced_labels_view.agent_columns.ExecutorsColumn",
            "io.jenkins.plugins.enhanced_labels_view.agent_columns.WrappedNodeMonitorColumn:hudson.node_monitors.ArchitectureMonitor",
            "io.jenkins.plugins.enhanced_labels_view.agent_columns.WrappedNodeMonitorColumn:hudson.node_monitors.DiskSpaceMonitor",
            "io.jenkins.plugins.enhanced_labels_view.agent_columns.WrappedNodeMonitorColumn:hudson.node_monitors.TemporarySpaceMonitor",
            "io.jenkins.plugins.enhanced_labels_view.agent_columns.ConfigureColumn"
    );

    @DataBoundConstructor
    public AgentDetailsLabelAction(Label label) {
        this.label = label;
    }

    public Label getLabel() {
        return label;
    }

    public List<AbstractAgentColumn> getColumns() {
        ArrayList<AbstractAgentColumn> agentColumns = new ArrayList<>();
        if (label instanceof LabelAtom la) {
            LabelAtomProperty lap = la.getPropertiesList().stream().filter(p -> p instanceof Property).findFirst().orElse(null);
            if (lap instanceof Property p) {
                return p.getAgentColumns();
            }
        }
        defaultColumns.forEach(c -> {
            try {
                String[] parts = c.split(":", 2);
                Class<?> clazz = Class.forName(parts[0]);
                if (AbstractAgentColumn.class.isAssignableFrom(clazz)) {
                    AbstractAgentColumn column = (AbstractAgentColumn) clazz.getDeclaredConstructor().newInstance();
                    if (column instanceof WrappedNodeMonitorColumn mw && parts.length > 1) {
                        mw.setMonitorClass(parts[1]);
                    }
                    agentColumns.add(column);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return agentColumns;
    }

    public List<AgentStatusSummary> getAgentStatusList() {
        List<AgentStatusSummary> agentStatusList = new ArrayList<>();
        AtomicInteger online = new AtomicInteger();
        AtomicInteger offline = new AtomicInteger();
        AtomicInteger notAcceptingTask = new AtomicInteger();
        AtomicInteger temporarilyOffline = new AtomicInteger();
        AtomicInteger idleOffline = new AtomicInteger();
        for (IComputer computer : label.getComputers()) {
            if (!computer.isAcceptingTasks()) {
                notAcceptingTask.getAndIncrement();
                continue;
            }
            var offlineCause = computer.getOfflineCause();
            if (offlineCause != null) {
                if (offlineCause instanceof OfflineCause.IdleOfflineCause) {
                    idleOffline.getAndIncrement();
                    continue;
                }
                if (offlineCause instanceof SimpleScheduledRetentionStrategy.ScheduledOfflineCause) {
                    notAcceptingTask.getAndIncrement();
                    continue;
                }
                if (offlineCause instanceof OfflineCause.UserCause) {
                    temporarilyOffline.getAndIncrement();
                    continue;
                }
                offline.getAndIncrement();
                continue;
            }
            if (computer.isOffline()) {
                offline.getAndIncrement();
            } else {
                online.getAndIncrement();
            }
        }
        agentStatusList.add(new AgentStatusSummary("symbol-computer", online.get(), "Online"));
        agentStatusList.add(new AgentStatusSummary("symbol-computer-paused", idleOffline.get(), "Idle"));
        agentStatusList.add(new AgentStatusSummary(
                "symbol-computer-disconnected", temporarilyOffline.get(), "Temporarily Offline"));
        agentStatusList.add(
                new AgentStatusSummary("symbol-computer-not-accepting", notAcceptingTask.get(), "Not Accepting Tasks"));
        agentStatusList.add(new AgentStatusSummary("symbol-computer-offline", offline.get(), "Offline"));
        return agentStatusList;
    }

    public Collection<? extends IComputer> getComputers() {
        return label.getComputers();
    }

    public record AgentStatusSummary(String symbol, int count, String tooltip) {}

    @Extension(ordinal = Integer.MIN_VALUE)
    public static class ActionFactory extends TransientActionFactory<Label> {

        @Override
        public Class<Label> type() {
            return Label.class;
        }

        @Override
        public @NonNull Collection<? extends Action> createFor(@NonNull Label target) {
            return List.of(new AgentDetailsLabelAction(target));
        }
    }
}
