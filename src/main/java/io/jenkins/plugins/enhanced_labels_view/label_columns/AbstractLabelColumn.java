package io.jenkins.plugins.enhanced_labels_view.label_columns;

import hudson.DescriptorExtensionList;
import hudson.ExtensionPoint;
import hudson.model.Describable;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;

public abstract class AbstractLabelColumn implements ExtensionPoint, Describable<AbstractLabelColumn> {

    public String getCaption() {
        return getDescriptor().getDisplayName();
    }

    public static DescriptorExtensionList<AbstractLabelColumn, Descriptor<AbstractLabelColumn>> all() {
        return Jenkins.get().getDescriptorList(AbstractLabelColumn.class);
    }

    public abstract static class AbstractLabelColumnDescriptor extends Descriptor<AbstractLabelColumn> {}
}
