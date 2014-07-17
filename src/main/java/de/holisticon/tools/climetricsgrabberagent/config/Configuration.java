package de.holisticon.tools.climetricsgrabberagent.config;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Configuration bean.
 * @author Tobias Gindler, Holisticon AG
 */
public class Configuration {

    private String nodeName;
    private int nrOfMetricThreads;

    private MetricConfiguration[] metrics;

    private OutputWriterConfig[] outputWriterConfigs;

    public OutputWriterConfig[] getOutputWriterConfigs() {
        return outputWriterConfigs;
    }

    @JsonProperty("outputWriterConfigs")
    public void setOutputWriterConfigs(OutputWriterConfig[] outputWriterConfigs) {
        this.outputWriterConfigs = outputWriterConfigs;
    }


    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public MetricConfiguration[] getMetrics() {
        return metrics;
    }

    public void setMetrics(MetricConfiguration[] metrics) {
        this.metrics = metrics;
    }

    public int getNrOfMetricThreads() {
        return nrOfMetricThreads;
    }

    public void setNrOfMetricThreads(int nrOfMetricThreads) {
        this.nrOfMetricThreads = nrOfMetricThreads;
    }


}
