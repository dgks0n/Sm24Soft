package com.sm24soft.common.view.velocity;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.config.XmlFactoryConfiguration;
import org.apache.velocity.tools.view.ViewToolManager;
import org.springframework.web.servlet.view.velocity.VelocityLayoutView;

/**
 * The class provides support for an auto-loaded, configurable toolbox. All the
 * toolbox will be added to velocity context
 * 
 * @author sondn
 * 
 */
public class VelocityToolsView extends VelocityLayoutView {
	
	private ViewToolManager toolManager;

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		
		XmlFactoryConfiguration config = new XmlFactoryConfiguration();
		config.read(getServletContext().getResourceAsStream(getToolboxConfigLocation()));
		toolManager = new ViewToolManager(getServletContext(), false, false);
		toolManager.configure(config);
		toolManager.setVelocityEngine(getVelocityEngine());
	}
	
	/**
	 *  Creates and returns an initialized Velocity context
	 */
	@Override
	protected Context createVelocityContext(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
		ToolContext toolContext = toolManager.createContext(request, response);
		VelocityContext context = new VelocityContext(toolContext);
		
		if (model != null) {
            for(Map.Entry<String, Object> entry : (Set<Map.Entry<String, Object>>) model.entrySet()) {
                context.put(entry.getKey(), entry.getValue());
            }
        }
		return context;
	}
}
