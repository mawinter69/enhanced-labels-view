package io.jenkins.plugins.enhanced_labels_view.agent_columns;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

public class ConfigureColumn extends AbstractAgentColumn {

    @DataBoundConstructor
    public ConfigureColumn() {
    }

    @Extension(ordinal = -1000)
    @Symbol("configure")
    public static class DescriptorImpl extends AbstractAgentColumnDescriptor {

        @Override
        @NonNull
        public String getDisplayName() {
            return "Configure";
        }
    }
}
