package io.jenkins.plugins.enhanced_labels_view.agent_columns;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.model.ComputerSet;
import hudson.node_monitors.TemporarySpaceMonitor;
import org.jenkinsci.Symbol;

public class TempSpaceColumn extends AbstractAgentColumn {

    public TemporarySpaceMonitor getMonitor() {
        return ComputerSet.getMonitors().get(TemporarySpaceMonitor.class);
    }

    @Extension
    @Symbol("freeTempSpace")
    public static class DescriptorImpl extends AbstractAgentColumn.AbstractAgentColumnDescriptor {

        @Override
        @NonNull
        public String getDisplayName() {
            return "Free Temp Space";
        }
    }
}
