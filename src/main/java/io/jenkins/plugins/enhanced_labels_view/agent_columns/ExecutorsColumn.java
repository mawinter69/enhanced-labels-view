package io.jenkins.plugins.enhanced_labels_view.agent_columns;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.model.Computer;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

public class ExecutorsColumn extends AbstractAgentColumn {

    @DataBoundConstructor
    public ExecutorsColumn() {
    }

    public String getExecutorsStatus(Computer c) {
        int onlineExecutors = c.countExecutors();
        if (c.isOffline()) {
            onlineExecutors = 0;
        }
        return c.getNumExecutors() + "/" + onlineExecutors + "/" + c.countBusy() + "/" + c.countIdle();
    }

    @Extension
    @Symbol("executors")
    public static class DescriptorImpl extends AbstractAgentColumnDescriptor {

        @Override
        @NonNull
        public String getDisplayName() {
            return "Executors";
        }
    }
}
