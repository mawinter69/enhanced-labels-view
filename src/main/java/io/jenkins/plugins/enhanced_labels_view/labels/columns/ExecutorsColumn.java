package io.jenkins.plugins.enhanced_labels_view.labels.columns;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import org.jenkinsci.Symbol;

public class ExecutorsColumn extends AbstractAgentColumn {

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
