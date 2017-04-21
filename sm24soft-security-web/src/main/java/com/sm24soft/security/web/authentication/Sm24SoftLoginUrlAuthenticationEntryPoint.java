package com.sm24soft.security.web.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.ELRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class Sm24SoftLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

	private static final RequestMatcher requestMatcher = new ELRequestMatcher("hasHeader('X-Requested-With','XMLHttpRequest')");
    
    /**
     * @param loginFormUrl
     *            URL where the login page can be found. Should either be
     *            relative to the web-app context path (include a leading
     *            {@code /}) or an absolute URL.
     */
    public Sm24SoftLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (isPreflight(request)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else if (isRestRequest(request)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        } else {
            super.commence(request, response, authException);
        }
    }
    
    /**
     * Checks if this is a X-domain pre-flight request.
     * 
     * @param request
     * @return
     */
    private boolean isPreflight(HttpServletRequest request) {
        return "OPTIONS".equals(request.getMethod());
    }

    /**
     * Checks if it is a rest request
     * 
     * @param request
     * @return
     */
    protected boolean isRestRequest(HttpServletRequest request) {
        return requestMatcher.matches(request);
    }

}
