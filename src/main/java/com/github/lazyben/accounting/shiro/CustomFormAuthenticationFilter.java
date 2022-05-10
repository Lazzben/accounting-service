package com.github.lazyben.accounting.shiro;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lazyben.accounting.exception.BizErrorCode;
import com.github.lazyben.accounting.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            } else {
                //allow them to see the login page ;)
                return true;
            }
        } else {
            //saveRequest(request);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            val errorResponse = ErrorResponse.builder()
                    .bizErrorCode(BizErrorCode.UN_AUTHORIZED)
                    .message("No access for related url")
                    .build();

            try (val writer = response.getWriter()) {
                writer.write(new ObjectMapper().writeValueAsString(errorResponse));
            } catch (IOException ex) {
                log.error("The IO exception is thrown");
                return false;
            }
            return false;
        }
    }
}
