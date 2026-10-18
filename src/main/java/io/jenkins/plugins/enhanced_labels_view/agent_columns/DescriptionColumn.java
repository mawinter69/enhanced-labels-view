package io.jenkins.plugins.enhanced_labels_view.agent_columns;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

public class DescriptionColumn extends AbstractAgentColumn {

    @DataBoundConstructor
    public DescriptionColumn() {
    }

    @Extension(ordinal = 1000)
    @Symbol("description")
    public static class DescriptorImpl extends AbstractAgentColumnDescriptor {

        @Override
        @NonNull
        public String getDisplayName() {
            return "Description";
        }
    }
}
