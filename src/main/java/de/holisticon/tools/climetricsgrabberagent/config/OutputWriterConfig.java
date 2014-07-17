package de.holisticon.tools.climetricsgrabberagent.config;

/**
 * Created by TGI on 04.07.14.
 */
public class OutputWriterConfig {

    private String outputWriterClassName;
    private InitParameter[] initParameters;

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

}
