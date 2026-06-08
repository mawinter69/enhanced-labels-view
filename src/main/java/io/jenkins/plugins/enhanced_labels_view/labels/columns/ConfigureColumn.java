package io.jenkins.plugins.enhanced_labels_view.labels.columns;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import org.jenkinsci.Symbol;

public class ConfigureColumn extends AbstractAgentColumn {

    @Extension(ordinal = -1000)
    @Symbol("configure")
    public static class DescriptorImpl extends AbstractAgentColumn.AbstractAgentColumnDescriptor {

        @Override
        @NonNull
        public String getDisplayName() {
            return "Configure";
        }
    }
}
