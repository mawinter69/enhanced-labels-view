package io.jenkins.plugins.enhanced_labels_view.label_columns;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.model.Label;
import org.jenkinsci.Symbol;

public class ExecutorsColumn extends AbstractLabelColumn {

    public String getExecutorsStatus(Label l) {
        return l.getTotalConfiguredExecutors() + "/" + l.getTotalExecutors() + "/" + l.getBusyExecutors() + "/"
                + l.getIdleExecutors();
    }

    @Extension
    @Symbol("executors")
    public static class DescriptorImpl extends AbstractLabelColumnDescriptor {

        @Override
        @NonNull
        public String getDisplayName() {
            return "Executors";
        }
    }
}
