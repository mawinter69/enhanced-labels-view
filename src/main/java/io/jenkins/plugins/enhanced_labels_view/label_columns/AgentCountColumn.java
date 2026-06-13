package io.jenkins.plugins.enhanced_labels_view.label_columns;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import org.jenkinsci.Symbol;

public class AgentCountColumn extends AbstractLabelColumn {

    @Extension
    @Symbol("agentCount")
    public static class DescriptorImpl extends AbstractLabelColumnDescriptor {

        @Override
        @NonNull
        public String getDisplayName() {
            return "Agent Count";
        }
    }
}
