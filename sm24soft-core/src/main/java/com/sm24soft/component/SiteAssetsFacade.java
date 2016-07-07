package com.sm24soft.component;

public class SiteAssetsFacade implements ISiteAssetsFacade {

	private String pageTitle;
	
	public SiteAssetsFacade(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	@Override
	public String getPageTitle() {
		return pageTitle;
	}

}
