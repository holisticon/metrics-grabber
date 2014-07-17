package de.holisticon.tools.climetricsgrabberagent.tools;

import de.holisticon.tools.climetricsgrabberagent.config.MetricConfiguration;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test class for {@link de.holisticon.tools.climetricsgrabberagent.tools.ActivateableFilter}.
 * @author  Tobias Gindler on 17.07.14.
 */
public class ActivateableFilterTest {

    @Test
    public void should_filter_array_correctly () {

        MetricConfiguration[] metricConfiguration = new MetricConfiguration[5];

        metricConfiguration[0] = createMetricConfig(true);
        metricConfiguration[1] = null;
        metricConfiguration[2] = createMetricConfig(false);
        metricConfiguration[3] = createMetricConfig(true);
        metricConfiguration[4] = createMetricConfig(false);

        MetricConfiguration[] result = ActivateableFilter.filterActivateableArray(metricConfiguration, MetricConfiguration.class);

        MatcherAssert.assertThat(result, Matchers.notNullValue());
        MatcherAssert.assertThat(result.length, Matchers.is(2));
        MatcherAssert.assertThat(result[0], Matchers.is(metricConfiguration[0]));
        MatcherAssert.assertThat(result[1], Matchers.is(metricConfiguration[3]));
    }


    private MetricConfiguration createMetricConfig(final boolean active) {
        MetricConfiguration instance = new MetricConfiguration();
        instance.setActive(active);

        return instance;
    }


}
