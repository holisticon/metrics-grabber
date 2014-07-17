package de.holisticon.monitoring.tools.metricsgrabber.core.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for reflection related tasks.
 * @author Tobias Gindler, Holisticon AG on 17.07.14.
 */
public final class ReflectionUtil {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * Hidden constructor.
     */
    private ReflectionUtil() {

    }

    public static <T> T createInstance(String className, Class<T> returnType) {

        T instance = null;

        if (className != null && returnType != null) {
            try {

                Class type = Class.forName(className);
                if (returnType.isAssignableFrom(type)){
                    instance = (T) type.newInstance();
                }

            } catch (Exception e) {
                instance = null;
            }
        }

        if (instance == null) {
            logger.error("Couldn't create instance of type {} with class name {}", returnType != null ? returnType.getCanonicalName() : "null", className);
        }

        return instance;
    }

}
