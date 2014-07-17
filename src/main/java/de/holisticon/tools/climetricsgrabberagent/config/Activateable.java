package de.holisticon.tools.climetricsgrabberagent.config;

/**
 * Interface to allow checks if an instance is active or not.
 * Mainly used at configuration classes.
 * @author Tobias Gindler, Holisticon AG on 17.07.14.
 */
public interface Activateable {

    /**
     * Checks whether an instance is active or not.
     * @return true, if the instance is active, otherwise false.
     */
    boolean isActive();

}
