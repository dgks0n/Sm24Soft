package com.sm24soft.interceptor;

import org.springframework.beans.factory.annotation.Autowired;

import com.sm24soft.component.ISiteAssetsFacade;

@org.springframework.stereotype.Component("DefaultProperties")
public class DefaultProperties {

	private ISiteAssetsFacade siteAssetsFacade;
	
	@Autowired
	public DefaultProperties(ISiteAssetsFacade siteAssetsFacade) {
		this.siteAssetsFacade = siteAssetsFacade;
	}
	
	public String getCurrentPageTitle() {
		return siteAssetsFacade.getPageTitle();
	}
	
	public String getCurrentTitleOfPage() {
		return siteAssetsFacade.getPageTitle();
	}

}
