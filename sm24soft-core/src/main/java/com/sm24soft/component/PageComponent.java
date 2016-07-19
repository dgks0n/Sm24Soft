package com.sm24soft.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Component("PageComponent")
public class PageComponent extends AuthenticationComponent {
	
	public static final String DEFAULT_ACTIVING_MENU_ITEM = "activeMenuItem";
	public static final String DEFAULT_ACTIVING_CHILD_MENU_ITEM = "activeChildMenuItem";
	
	private HttpServletRequest request;
	private ISiteAssetsFacade siteAssetsFacade;
	
	@Autowired
	public PageComponent(final HttpServletRequest request, final ISiteAssetsFacade siteAssetsFacade,
			final IAuthenticationFacade authenticationFacade) {
		super(authenticationFacade);
		
		this.request = request;
		this.siteAssetsFacade = siteAssetsFacade;
	}
	
	public String getActivingMenuItem() {
		return getCurrentActivingMenuItem(DEFAULT_ACTIVING_MENU_ITEM);
	}
	
	public String getActiveChildMenuItem() {
		return getCurrentActivingMenuItem(DEFAULT_ACTIVING_CHILD_MENU_ITEM);
	}
	
	public String getCurrentPageTitle() {
		return siteAssetsFacade.getPageTitle();
	}
	
	public String getCurrentTitleOfPage() {
		return siteAssetsFacade.getPageTitle();
	}
	
	public String getPageCopyright() {
		return siteAssetsFacade.getPageCopyright();
	}
	
	private String getCurrentActivingMenuItem(String attribute) {
		if (null == request) {
			throw new NullPointerException("The HttpServletRequest is null");
		}
		final HttpSession session = request.getSession();
		if (null != session) {
			final Object object = session.getAttribute(attribute);
			if (null != object) {
				return (String) object;
			}
		}
		return StringUtils.EMPTY;
	}
	
	public String markSelectElement(String sourceVal, String targetVal) {
		if (StringUtils.isEmpty(sourceVal)) {
			return StringUtils.EMPTY;
		}
		
		if (sourceVal.equals(targetVal)) {
			return "selected";
		}
		return StringUtils.EMPTY;
	}
}
