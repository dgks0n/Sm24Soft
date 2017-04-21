package com.sm24soft.common.view.velocity;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.velocity.VelocityLayoutView;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

/**
 * Convenience subclass of VelocityViewResolver, adding support for
 * VelocityLayoutView and its properties.
 * 
 * <p>See VelocityViewResolver's javadoc for general usage info.</p>
 * 
 * @author sondn
 * 
 */
public class VelocityMultipleViewResolver extends VelocityViewResolver {

	private static final Logger logger = LoggerFactory.getLogger(VelocityMultipleViewResolver.class); 

	private Map<String, String> mappings = new HashMap<String, String>();

	private String layoutKey = VelocityLayoutView.DEFAULT_LAYOUT_KEY;
	private String screenContentKey = VelocityLayoutView.DEFAULT_SCREEN_CONTENT_KEY;

	@Override
	protected Class<?> requiredViewClass() {
		return VelocityLayoutView.class;
	}

	@Override
	protected void initApplicationContext() {
		super.initApplicationContext();
	}

	@Override
	protected AbstractUrlBasedView buildView(final String viewName) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Building view using multiple layout resolver. View name is {}", viewName);
		}

		VelocityLayoutView view = (VelocityLayoutView) super.buildView(viewName);
		if (layoutKey != null) {
			view.setLayoutKey(layoutKey);
		}

		if (screenContentKey != null) {
			view.setScreenContentKey(screenContentKey);
		}

		if (!this.mappings.isEmpty()) {
			for (Map.Entry<String, String> entry : this.mappings.entrySet()) {
				// Correct wildcards so that we can use the matches method in the String object
				String mappingRegexp = StringUtils.replace(entry.getKey(), "*", ".*");
				// If the view matches the regexp mapping
				if (viewName.matches(mappingRegexp)) {
					if (logger.isDebugEnabled()) {
						logger.debug("Found matching view. Setting layout url to {}", entry.getValue());
					}

					view.setLayoutUrl(entry.getValue());
					return view;
				}
			}
		}

		return view;
	}

	public Map<String, String> getMappings() {
		return mappings;
	}

	public void setMappings(final Map<String, String> mappings) {
		this.mappings = mappings;
	}

	/**
	 * Set the context key used to specify an alternate layout to be used instead
	 * of the default layout. Screen content templates can override the layout
	 * template that they wish to be wrapped with by setting this value in the
	 * template, for example:<br>
	 * <code>#set( $layout = "MyLayout.html" )</code>
	 * <p>The default key is "layout", as illustrated above.
	 * @param layoutKey the name of the key you wish to use in your
	 * screen content templates to override the layout template
	 * @see VelocityLayoutView#setLayoutKey
	 * 
	 */
	public void setLayoutKey(final String layoutKey) {
		this.layoutKey = layoutKey;
	}

	/**
	 * Set the name of the context key that will hold the content of
	 * the screen within the layout template. This key must be present
	 * in the layout template for the current screen to be rendered.
	 * <p>Default is "screen_content": accessed in VTL as
	 * <code>$screen_content</code>.
	 * @param screenContentKey the name of the screen content key to use
	 * @see VelocityLayoutView#setScreenContentKey
	 * 
	 */
	public void setScreenContentKey(final String screenContentKey) {
		this.screenContentKey = screenContentKey;
	}
	
}
