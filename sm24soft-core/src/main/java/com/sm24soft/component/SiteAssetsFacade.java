package com.sm24soft.component;

public class SiteAssetsFacade implements ISiteAssetsFacade {

	private String pageTitle;
	private String pageCopyright;
	
	public SiteAssetsFacade(String pageTitle, String pageCopyright) {
		this.pageTitle = pageTitle;
		this.pageCopyright = pageCopyright;
	}

	@Override
	public String getPageTitle() {
		return this.pageTitle;
	}

	@Override
	public String getPageCopyright() {
		return this.pageCopyright;
	}

}
