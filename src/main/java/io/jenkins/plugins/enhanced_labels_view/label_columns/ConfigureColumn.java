package io.jenkins.plugins.enhanced_labels_view.label_columns;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.model.Label;
import hudson.model.labels.LabelAtom;
import org.jenkinsci.Symbol;

public class ConfigureColumn extends AbstractLabelColumn {

    public boolean isConfigurable(Label label) {
        return label instanceof LabelAtom;
    }

    @Extension(ordinal = -1000)
    @Symbol("executors")
    public static class DescriptorImpl extends AbstractLabelColumn.AbstractLabelColumnDescriptor {

        @Override
        @NonNull
        public String getDisplayName() {
            return "Configure";
        }
    }
}
