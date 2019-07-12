package com.thinkgem.jeesite.common.filter;

import com.google.common.base.Joiner;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by xingfinal on 16/4/28.
 */
@Component
@Order(3)
public class RequestLoggingFilter extends AbstractRequestLoggingFilter {
    private Log log = LogFactory.getLog(this.getClass());

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        try {
            InputStream stream = request.getInputStream();
            //String body = new String(ByteStreams.toByteArray(stream), StandardCharsets.UTF_8);

            Map<String, String[]> parameterMap = request.getParameterMap();
            StringBuilder parameterBuilder = new StringBuilder();
            parameterMap.forEach((k, v) ->
                    parameterBuilder.append(k).append(" -> ")
                            .append(Joiner.on(" && ").skipNulls().join(v)).append(" || "));

            final StringBuilder builder = new StringBuilder("REST Request --\n")
                    .append("[HTTP METHOD: ").append(request.getMethod()).append("]\n")
                    .append("[REQUEST URI: ").append(request.getRequestURI()).append("]\n")
                    .append("[REQUEST PARAMETERS: ").append(parameterBuilder.toString()).append("]\n")
                    //.append("[REQUEST BODY: ").append(body).append("]\n")
                    .append("[REMOTE ADDRESS: ").append(request.getRemoteAddr()).append("]");
            log.info(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
    }
}
