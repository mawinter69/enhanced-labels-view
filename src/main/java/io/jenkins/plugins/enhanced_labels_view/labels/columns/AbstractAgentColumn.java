package io.jenkins.plugins.enhanced_labels_view.labels.columns;

import hudson.DescriptorExtensionList;
import hudson.ExtensionPoint;
import hudson.model.Describable;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;

public abstract class AbstractAgentColumn implements ExtensionPoint, Describable<AbstractAgentColumn> {

    public String getCaption() {
        return getDescriptor().getDisplayName();
    }

    public static DescriptorExtensionList<AbstractAgentColumn, Descriptor<AbstractAgentColumn>> all() {
        return Jenkins.get().getDescriptorList(AbstractAgentColumn.class);
    }

    public abstract static class AbstractAgentColumnDescriptor extends Descriptor<AbstractAgentColumn> {}
}
