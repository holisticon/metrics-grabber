package de.holisticon.monitoring.tools.metricsgrabber.core.output;

/**
 *  Output writer execption that will be thrown if an output writer can't be initialized correctly.
 *  @author Tobias Gindler, Holisticon AG
 */
public class InitParameterException extends Exception{

    public InitParameterException() {
       super();
    }

    public InitParameterException(final String message) {
        super(message);
    }

    public InitParameterException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
