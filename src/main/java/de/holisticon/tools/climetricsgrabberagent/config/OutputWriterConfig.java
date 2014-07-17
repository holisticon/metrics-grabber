package de.holisticon.tools.climetricsgrabberagent.config;

import de.holisticon.tools.climetricsgrabberagent.Activateable;

/**
 * Class that encapsulates a output writer configuration.
 * @author  Tobias Gindler, Holisticon AG on 04.07.14.
 */
public class OutputWriterConfig implements Activateable{

    private String outputWriterClassName;
    private InitParameter[] initParameters;
    private boolean active = true;

    public String getOutputWriterClassName() {
        return outputWriterClassName;
    }

    public void setOutputWriterClassName(String outputWriterClassName) {
        this.outputWriterClassName = outputWriterClassName;
    }

    public InitParameter[] getInitParameters() {
        return initParameters;
    }

    public void setInitParameters(InitParameter[] initParameters) {
        this.initParameters = initParameters;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
