package io.jenkins.plugins.enhanced_labels_view.agents;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.model.Action;
import hudson.model.Computer;
import hudson.model.Descriptor;
import hudson.model.Label;
import hudson.model.Node;
import io.jenkins.plugins.enhanced_labels_view.Messages;
import io.jenkins.plugins.enhanced_labels_view.label_columns.AbstractLabelColumn;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import jenkins.model.TransientActionFactory;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.StaplerRequest2;

public class ExtendedLabelsAction implements Action {

    private final Computer computer;

    public ExtendedLabelsAction(Computer computer) {
        this.computer = computer;
    }

    public Computer getComputer() {
        return computer;
    }

    public List<AbstractLabelColumn> getColumns() {
        List<AbstractLabelColumn> columns = new ArrayList<>();
        final JSONObject emptyJSON = new JSONObject();
        AbstractLabelColumn.all().forEach(c -> {
            try {
                columns.add(c.newInstance((StaplerRequest2) null, emptyJSON));
            } catch (Descriptor.FormException e) {
                throw new RuntimeException(e);
            }
        });
        return columns;
    }

    public LabelStatus getLabelStatus(Label label) {
        Set<Node> nodes = label.getNodes();
        long onlineCount = nodes.stream()
                .filter(n -> {
                    Computer c = n.toComputer();
                    return c != null && !c.isOffline();
                })
                .count();
        if (onlineCount == nodes.size()) {
            return new LabelStatus("symbol-computer", "Online");
        }
        if (onlineCount == 0) {
            return new LabelStatus("symbol-computer-offline", "All offline");
        }
        return new LabelStatus("symbol-computer-disconnected", "Some offline");
    }

    public record LabelStatus(String symbol, String tooltip) {}
    ;

    @Override
    public String getIconFileName() {
        return "symbol-pricetags-outline plugin-ionicons-api";
    }

    @Override
    public String getDisplayName() {
        return Messages.ExtendedLabelsAction_displayName();
    }

    @Override
    public String getUrlName() {
        return "extendedLabels";
    }

    @Extension
    public static class ActionFactory extends TransientActionFactory<Computer> {
        @Override
        public Class<Computer> type() {
            return Computer.class;
        }

        @Override
        @NonNull
        public java.util.List<? extends Action> createFor(@NonNull Computer computer) {
            return java.util.Collections.singletonList(new ExtendedLabelsAction(computer));
        }
    }
}
