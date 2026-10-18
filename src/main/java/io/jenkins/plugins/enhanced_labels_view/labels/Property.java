package io.jenkins.plugins.enhanced_labels_view.labels;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.model.labels.LabelAtomProperty;
import hudson.model.labels.LabelAtomPropertyDescriptor;
import io.jenkins.plugins.enhanced_labels_view.agent_columns.AbstractAgentColumn;
import java.util.ArrayList;
import java.util.List;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

public class Property extends LabelAtomProperty {

    private List<AbstractAgentColumn> agentColumns = new ArrayList<>();

    @DataBoundConstructor
    public Property() {}

    public List<AbstractAgentColumn> getAgentColumns() {
        return agentColumns;
    }

    @DataBoundSetter
    public void setAgentColumns(List<AbstractAgentColumn> agentColumns) {
        this.agentColumns = agentColumns;
    }

    @Extension
    @Symbol("enhancedLabelsViewProperty")
    public static final class DescriptorImpl extends LabelAtomPropertyDescriptor {

        @Override
        @NonNull
        public String getDisplayName() {
            return "Enhanced Labels View Property";
        }
    }
}
