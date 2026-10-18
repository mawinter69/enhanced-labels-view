package io.jenkins.plugins.enhanced_labels_view.agent_columns;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.model.ComputerSet;
import hudson.node_monitors.NodeMonitor;
import hudson.util.ListBoxModel;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.verb.POST;

public class WrappedNodeMonitorColumn extends AbstractAgentColumn {

    private String monitorClass;

    @DataBoundConstructor
    public WrappedNodeMonitorColumn() {
    }

    @DataBoundSetter
    public void setMonitorClass(String monitorClass) {
        this.monitorClass = monitorClass;
    }

    public String getMonitorClass() {
        return monitorClass;
    }

    public NodeMonitor getMonitor() {
        return ComputerSet.getMonitors().stream().filter(m -> m.getClass().getName().equals(monitorClass)).findFirst().orElse(null);
    }

    @Override
    public String getCaption() {
        NodeMonitor monitor = getMonitor();
        return monitor != null ? monitor.getColumnCaption() : null;
    }

    @Extension
    @Symbol("wrappedNodeMonitor")
    public static class DescriptorImpl extends AbstractAgentColumnDescriptor {

        @Override
        @NonNull
        public String getDisplayName() {
            return "Wrapped Node Monitor";
        }

        @POST
        public ListBoxModel doFillMonitorClassItems() {
            ListBoxModel model = new ListBoxModel();
            for (NodeMonitor monitor : ComputerSet.getMonitors()) {
                String className = monitor.getClass().getName();
                ListBoxModel.Option option = new ListBoxModel.Option(monitor.getDescriptor().getDisplayName(), className);
                model.add(option);
            }
            return model;
        }
    }

}
