package de.holisticon.tools.climetricsgrabberagent.tools;

import de.holisticon.tools.climetricsgrabberagent.config.Activateable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for filtering {@link de.holisticon.tools.climetricsgrabberagent.config.Activateable} arrays.
 * @author Tobias Gindler, Holisticon AG on 17.07.14.
 */
public final class ActivateableFilter {

    /**
     * Hide constructor.
     */
    private ActivateableFilter () {

    }

    /**
     * Filters an array of instances of type marked with the {@link de.holisticon.tools.climetricsgrabberagent.config.Activateable} interface.
     * The filter keeps the order of elements.
     * @param arrayToFilter the array to filter
     * @param type The type of the array(must extend {@link de.holisticon.tools.climetricsgrabberagent.config.Activateable})
     * @param <T> the generic type of the array
     * @return a filtered array, containing all active instances
     */
    public static <T extends Activateable> T[] filterActivateableArray (final T[] arrayToFilter, Class<T> type) {

        List<T> list = new ArrayList<T>();

        if (arrayToFilter != null) {

            for (T element : arrayToFilter) {
                if (element != null && element.isActive()) {
                    list.add(element);
                }
            }

        }

        return list.toArray((T[])Array.newInstance(type,list.size()));

    }
}
