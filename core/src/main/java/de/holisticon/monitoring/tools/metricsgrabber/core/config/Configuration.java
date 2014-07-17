package de.holisticon.monitoring.tools.metricsgrabber.core.config;

/**
 * Configuration bean.
 *
 * @author Tobias Gindler, Holisticon AG
 */
public class Configuration {

    private String nodeName;
    private int nrOfMetricThreads;
    private MetricReaderConfig[] metricReaders;
    private OutputWriterConfig[] outputWriterConfigs;

    public OutputWriterConfig[] getOutputWriterConfigs() {
        return outputWriterConfigs;
    }

    public void setOutputWriterConfigs(OutputWriterConfig[] outputWriterConfigs) {
        this.outputWriterConfigs = outputWriterConfigs;
    }


    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }


    public int getNrOfMetricThreads() {
        return nrOfMetricThreads;
    }

    public void setNrOfMetricThreads(int nrOfMetricThreads) {
        this.nrOfMetricThreads = nrOfMetricThreads;
    }

    public MetricReaderConfig[] getMetricReaders() {
        return metricReaders;
    }

    public void setMetricReaders(MetricReaderConfig[] metricReaders) {
        this.metricReaders = metricReaders;
    }
}
