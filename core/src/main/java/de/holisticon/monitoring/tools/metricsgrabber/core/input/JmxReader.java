package de.holisticon.monitoring.tools.metricsgrabber.core.input;

import de.holisticon.monitoring.tools.metricsgrabber.core.Metric;
import de.holisticon.monitoring.tools.metricsgrabber.core.common.AbstractInitializeable;
import de.holisticon.monitoring.tools.metricsgrabber.core.config.MetricConfiguration;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Reader implementation for reading metrics via jmx.
 * @author Tobias Gindler, Holisticon AG on 17.07.14.
 */
public class JmxReader extends AbstractInitializeable implements MetricReader{

    @Override
    public List<Metric> readMetrics(MetricConfiguration metricConfiguration) {
        try {
            JMXServiceURL url = new JMXServiceURL("service:jmx:remote-jmx://localhost:9999");
            //JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9999/jmxrmi");


            JMXConnector jmxc = JMXConnectorFactory.connect(url, null);

            MBeanServerConnection mbsc =
                    jmxc.getMBeanServerConnection();


            List<String> configuredAttributes = metricConfiguration.getAttributes() != null ? new ArrayList<String>() : Arrays.asList(metricConfiguration.getAttributes());

            ObjectName objectName = new ObjectName(metricConfiguration.getMetricQueryString());

            Set<ObjectName> queryNames = mbsc.queryNames(objectName, null);
            for (ObjectName queryName : queryNames) {

                MBeanInfo mBeanInfo = mbsc.getMBeanInfo(queryName);
                ObjectInstance objectInstance = mbsc.getObjectInstance(queryName);

                List queryAttributeList = new ArrayList(configuredAttributes);
                if (queryAttributeList.size() == 0) {
                    MBeanAttributeInfo[] attrs = mBeanInfo.getAttributes();
                    for (MBeanAttributeInfo attrInfo : attrs) {
                        queryAttributeList.add(attrInfo.getName());
                    }
                }


                if (queryAttributeList.size() > 0) {

                    String[] queryAttributes = (String[])queryAttributeList.toArray(new String[queryAttributeList.size()]);


                    AttributeList attributeList = mbsc.getAttributes(queryName, queryAttributes);
                    for (Attribute attribute : attributeList.asList()) {
                        System.out.println(attribute.getName());
                    }

                }



            }


            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while accessing JMX bean",e);
        }
    }


}
