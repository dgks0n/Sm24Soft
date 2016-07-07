package com.sm24soft.security.web.authentication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class Sm24SoftUrlLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {
	
	private List<String> cookiesToClear;
	
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    	final HttpSession session = request.getSession();
    	if (session != null) {
    		session.invalidate();
    	}
    	
    	if (CollectionUtils.isEmpty(cookiesToClear)) {
    		Cookie[] cookies = request.getCookies();
    		if (cookies != null) {
    			if (cookiesToClear == null) {
    				cookiesToClear = new ArrayList<String>();
    			}
    			for (Cookie cookie : cookies) {
    				cookiesToClear.add(cookie.getName());
    			}
    		}
    	}
    	
    	Assert.notNull(cookiesToClear, "List of cookies cannot be null");
    	
        for (String cookieName : cookiesToClear) {
            Cookie cookie = new Cookie(cookieName, null);
            String cookiePath = request.getContextPath();
            if (!StringUtils.hasLength(cookiePath)) {
                cookiePath = "/";
            }
            cookie.setPath(cookiePath);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        
        SecurityContextHolder.getContext().setAuthentication(null);
		SecurityContextHolder.clearContext();
    	
    	super.onLogoutSuccess(request, response, null);
    }

	public List<String> getCookiesToClear() {
		return cookiesToClear;
	}

	public void setCookiesToClear(List<String> cookiesToClear) {
		this.cookiesToClear = cookiesToClear;
	}
	
}
