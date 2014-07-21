package de.holisticon.monitoring.tools.metricsgrabber.core.tools;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.concurrent.ThreadFactory;

/**
 * Test class for {@link de.holisticon.monitoring.tools.metricsgrabber.core.tools.NamedThreadFactory}.
 * @author Tobias Gindler, Holisticon AG on 18.07.14.
 */
public class NamedThreadFactoryTest {


    @Test
    public void should_create_daemon_thread_correctly() {
        final String givenNamePrefix = "PREFIX";
        final boolean givenIsDaemon = true;
        NamedThreadFactory namedThreadFactory = new NamedThreadFactory(givenNamePrefix, givenIsDaemon);


        Thread createdThread = namedThreadFactory.newThread(new Runnable() {
            @Override
            public void run() {

            }
        });

        MatcherAssert.assertThat(createdThread.getName(), Matchers.startsWith(givenNamePrefix));
        MatcherAssert.assertThat(createdThread.isDaemon(), Matchers.is(givenIsDaemon));

    }

    @Test
    public void should_create_non_daemon_thread_correctly() {
        final String givenNamePrefix = "PREFIX";
        final boolean givenIsDaemon = false;
        NamedThreadFactory namedThreadFactory = new NamedThreadFactory(givenNamePrefix, givenIsDaemon);


        Thread createdThread = namedThreadFactory.newThread(new Runnable() {
            @Override
            public void run() {

            }
        });

        MatcherAssert.assertThat(createdThread.getName(), Matchers.startsWith(givenNamePrefix));
        MatcherAssert.assertThat(createdThread.isDaemon(), Matchers.is(givenIsDaemon));

    }

}
