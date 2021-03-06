package de.holisticon.monitoring.tools.metricsgrabber.core.common;

import de.holisticon.monitoring.tools.metricsgrabber.core.config.InitParameter;
import de.holisticon.monitoring.tools.metricsgrabber.core.output.InitParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TGI on 17.07.14.
 */
public abstract class AbstractInitializeable implements Initializable{

    private static final Logger logger = LoggerFactory.getLogger(AbstractInitializeable.class);


    private Map<String,String> initParameterMap;

    public void setInitParameters (final InitParameter[] initParameters) {

        Map<String,String> tmpMap = new HashMap<String, String>();

        if (initParameters != null) {
            for (InitParameter initParameter: initParameters) {
                tmpMap.put(initParameter.getName(), initParameter.getValue());
            }
        }

        this.initParameterMap = tmpMap;

    }

    protected String getInitParameter (final String parameterName) {

        String returnValue = null;

        if (this.initParameterMap != null) {
            returnValue = this.initParameterMap.get(parameterName);
        }

        return returnValue;
    }

    public Integer getInitParameterAsInteger(final String parameterName) throws InitParameterException {

        String value = this.getInitParameter(parameterName);

        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            throw new InitParameterException("Can not convert init parameter '" + parameterName + "' with value '" + value + "' to Integer ");
        }
    }

    public Long getInitParameterAsLong(final String parameterName) throws InitParameterException{

        String value = this.getInitParameter(parameterName);

        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            throw new InitParameterException("Can not convert init parameter '" + parameterName + "' with value '" + value + "' to Long ");
        }
    }

}
