package de.holisticon.tools.climetricsgrabberagent;

import de.holisticon.tools.climetricsgrabberagent.config.InitParameter;
import de.holisticon.tools.climetricsgrabberagent.config.MetricConfiguration;

import java.util.List;

/**
 * Interface for metric readers.
 * @author Tobias Gindler, Holisticon AG on 17.07.14.
 */
public interface MetricReader {

    /**
     * Reads metric for a specific metric configuration.
     * @return the attributes read for the passed metric configuration
     */
    List<Metric> readMetrics(final MetricConfiguration metricConfiguration);

    /**
     * Sets init parameters. Must normally be called before the init method.
     * @param initParameters the init parameters to set
     */
    void setInitParameters (final InitParameter[] initParameters);
}
