package io.jenkins.plugins.enhanced_labels_view.agent_columns;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.model.ComputerSet;
import hudson.node_monitors.DiskSpaceMonitor;
import org.jenkinsci.Symbol;

public class FreeDiskSpaceColumn extends AbstractAgentColumn {

    public DiskSpaceMonitor getMonitor() {
        return ComputerSet.getMonitors().get(DiskSpaceMonitor.class);
    }

    @Extension
    @Symbol("freeDiskSpace")
    public static class DescriptorImpl extends AbstractAgentColumn.AbstractAgentColumnDescriptor {

        @Override
        @NonNull
        public String getDisplayName() {
            return "Free Disk Space";
        }
    }
}
