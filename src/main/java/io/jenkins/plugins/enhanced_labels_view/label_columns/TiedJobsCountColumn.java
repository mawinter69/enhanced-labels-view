package io.jenkins.plugins.enhanced_labels_view.label_columns;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import org.jenkinsci.Symbol;

public class TiedJobsCountColumn extends AbstractLabelColumn {

    @Extension
    @Symbol("tiedJobsCount")
    public static class DescriptorImpl extends AbstractLabelColumnDescriptor {

        @Override
        @NonNull
        public String getDisplayName() {
            return "Tied Jobs Count";
        }
    }
}
