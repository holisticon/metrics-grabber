package de.holisticon.monitoring.tools.metricsgrabber.core.tools;

/**
 * Utility class for String related tasks.
 *
 * @author Tobias Gindler, Holisticon AG on 18.07.14.
 */
public final class StringUtil {

    /**
     * Hidden Constructor
     */
    private StringUtil() {

    }

    public static String replaceWhitespaces(final String input) {

        return input.replaceAll("\\s", "_");

    }


}
