package de.holisticon.tools.climetricsgrabberagent.input;

import de.holisticon.tools.climetricsgrabberagent.common.Initializable;
import de.holisticon.tools.climetricsgrabberagent.Metric;
import de.holisticon.tools.climetricsgrabberagent.config.MetricConfiguration;

import java.util.List;

/**
 * Interface for metric readers.
 * @author Tobias Gindler, Holisticon AG on 17.07.14.
 */
public interface MetricReader extends Initializable{

    /**
     * Reads metric for a specific metric configuration.
     * @return the attributes read for the passed metric configuration
     */
    List<Metric> readMetrics(final MetricConfiguration metricConfiguration);


}
