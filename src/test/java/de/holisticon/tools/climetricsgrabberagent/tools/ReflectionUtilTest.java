package de.holisticon.tools.climetricsgrabberagent.tools;

import de.holisticon.tools.climetricsgrabberagent.input.MetricReader;
import de.holisticon.tools.climetricsgrabberagent.output.OutputWriter;
import de.holisticon.tools.climetricsgrabberagent.input.JbossCliReader;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.hamcrest.Matchers;

/**
 * Test class for {@link de.holisticon.tools.climetricsgrabberagent.tools.ReflectionUtil}.
 * @author  Tobias Gindler, Holisticon AG on 17.07.14.
 */
public class ReflectionUtilTest {

    @Test
    public void createInstance_should_create_instance() {

        String givenClassName = JbossCliReader.class.getCanonicalName();
        Class<MetricReader> givenResultType = MetricReader.class;

        MetricReader reader = ReflectionUtil.createInstance(givenClassName,givenResultType);
        
        MatcherAssert.assertThat(reader, Matchers.notNullValue());


    }

    @Test
         public void createInstance_should__not_create_instance_for_not_existing_type() {

        String givenClassName = JbossCliReader.class.getCanonicalName() + "XXX";
        Class<MetricReader> givenResultType = MetricReader.class;

        MetricReader reader = ReflectionUtil.createInstance(givenClassName,givenResultType);

        MatcherAssert.assertThat(reader, Matchers.nullValue());


    }

    @Test
    public void createInstance_should__not_create_instance_for_not_matching_type() {

        String givenClassName = JbossCliReader.class.getCanonicalName();
        Class<OutputWriter> givenResultType = OutputWriter.class;

        OutputWriter reader = ReflectionUtil.createInstance(givenClassName,givenResultType);

        MatcherAssert.assertThat(reader, Matchers.nullValue());


    }

    @Test
    public void createInstance_should__not_create_instance_for_null_valued_class_name() {

        String givenClassName = null;
        Class<MetricReader> givenResultType = MetricReader.class;

        MetricReader reader = ReflectionUtil.createInstance(givenClassName,givenResultType);

        MatcherAssert.assertThat(reader, Matchers.nullValue());


    }

    @Test
    public void createInstance_should__not_create_instance_for_null_valued_return_type() {

        String givenClassName = JbossCliReader.class.getCanonicalName();
        Class<MetricReader> givenResultType = null;

        MetricReader reader = ReflectionUtil.createInstance(givenClassName,givenResultType);

        MatcherAssert.assertThat(reader, Matchers.nullValue());


    }

}
