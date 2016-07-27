package com.sm24soft.component;

import java.io.File;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Component("PageComponent")
public class PageComponent extends AuthenticationComponent {
	
	private ServletContext context;
	
	private ISiteAssetsFacade siteAssetsFacade;
	
	@Autowired
	public PageComponent(ServletContext context, ISiteAssetsFacade siteAssetsFacade, 
			IAuthenticationFacade authenticationFacade) {
		super(authenticationFacade);
		
		this.context = context;
		this.siteAssetsFacade = siteAssetsFacade;
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
	
	public String markSelectElement(String sourceVal, String targetVal) {
		if (StringUtils.isEmpty(sourceVal)) {
			return StringUtils.EMPTY;
		}
		
		if (sourceVal.equals(targetVal)) {
			return "selected";
		}
		return StringUtils.EMPTY;
	}
	
	public String getResourcePath(String resourcePath) {
		if (StringUtils.isEmpty(resourcePath)) {
			return resourcePath;
		}
		String realPath = context.getRealPath(File.separator);
		return resourcePath.replace(realPath, context.getContextPath() + File.separator);
	}
}
