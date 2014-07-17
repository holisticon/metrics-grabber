package de.holisticon.tools.climetricsgrabberagent.input;

import de.holisticon.tools.climetricsgrabberagent.common.AbstractInitializeable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract metric reader class.
 * Provides access to configuration.
 * @author Tobias Gindler, Holisticon AG on 17.07.14.
 */
public abstract class AbstractMetricReader extends AbstractInitializeable implements MetricReader{

    protected static final Logger logger = LoggerFactory.getLogger(AbstractMetricReader.class);


}
