package de.holisticon.monitoring.tools.metricsgrabber.core.tools;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test class for {@link de.holisticon.monitoring.tools.metricsgrabber.core.tools.StringUtil}.
 * @author Tobias Gindler, Holisticon AG on 18.07.14.
 */
public class StringUtilTest {

    @Test
    public void replaceWhitespaces_should_do_replace_correctly() {

        final String givenString = "abc   def\nghi\tjkl";
        final String expectedResult = "abc___def_ghi_jkl";

        final String result = StringUtil.replaceWhitespaces(givenString);

        MatcherAssert.assertThat(result, Matchers.is(expectedResult));

    }

}
