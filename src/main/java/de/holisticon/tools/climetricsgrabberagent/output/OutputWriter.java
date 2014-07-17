package de.holisticon.tools.climetricsgrabberagent.output;

import de.holisticon.tools.climetricsgrabberagent.common.Initializable;
import de.holisticon.tools.climetricsgrabberagent.Metric;

import java.util.List;

/**
 * Interface for output writers.
 * @author Tobias Gindler, Holisticon AG
 */
public interface OutputWriter extends Initializable{

    /**
     * Writes metrics via output writer.
     * @param metrics
     */
    void writeMetrics( List<Metric> metrics);

    /**
     * Initializes OutputWriter
     * @param nodeIdentifier the node identifier used to prefix metrics data
     */
    void init(final String nodeIdentifier) throws InitParameterException;

}
