package com.sm24soft.controller.backoffice;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sm24soft.common.exception.DuplicateKeyException;
import com.sm24soft.common.exception.ObjectNotFoundException;
import com.sm24soft.controller.ApplicationController;
import com.sm24soft.entity.MenuItem;
import com.sm24soft.http.response.HttpResponse;
import com.sm24soft.service.IMenuItemService;

@org.springframework.stereotype.Controller
@RequestMapping("/admin/menu-item")
public class MenuItemController extends ApplicationController {
	
	private static final Logger logger = LoggerFactory.getLogger(MenuItemController.class);

	private IMenuItemService menuItemService;
	
	@Autowired
	public MenuItemController(IMenuItemService menuItemService) {
		this.menuItemService = menuItemService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String renderMenuItemsPage(final HttpServletRequest request, final Model model) {
		logger.info("Call renderMenuItemsPage()");
		
		List<MenuItem> listOfMenuItems = new ArrayList<>();
		try {
			listOfMenuItems = menuItemService.findAllMenuItems();
		} catch (Exception ex) {
			logger.warn(ex.getMessage(), ex);
		}
		
		model.addAttribute("listOfMenuItems", listOfMenuItems);
		return "back-office/menu-item/list";
	}
	
	@RequestMapping(path = { "/create-new" }, method = RequestMethod.GET)
	public String renderCreateNewOneMenuItemPage() {
		return "back-office/menu-item/create-new";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody HttpResponse<String> createNewOneMenuItem(@RequestBody MenuItem menuItem) {
		
		try {
			menuItemService.creatNewMenuItem(menuItem);
		} catch (DuplicateKeyException ex) {
			logger.warn(ex.getMessage());
			
			return getErrorStatus(ex);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			
			return getErrorStatus(ex);
		}
		return getOKStatus();
	}
	
	@RequestMapping(path = { "/{id}" }, method = RequestMethod.DELETE)
	public @ResponseBody HttpResponse<String> deleteOneMenuItem(@PathVariable("id") String id) {
		try {
			menuItemService.deleteById(id);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return getErrorStatus(ex);
		}
		return getOKStatus();
	}
	
	@RequestMapping(path = { "/{id}" }, method = RequestMethod.GET)
	public String renderUpdateOneMenuItemPage(@PathVariable("id") String id, final Model model) {
		MenuItem menuItem = null;
		
		try {
			menuItem = menuItemService.findById(id);
		} catch (IllegalArgumentException | ObjectNotFoundException ex) {
			logger.error(ex.getMessage(), ex);
			
			return redirectToError(HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			model.addAttribute("menuItem", menuItem);
		}
		return "back-office/menu-item/update";
	}
	
	@RequestMapping(path = { "/{id}" }, method = RequestMethod.PUT)
	public @ResponseBody HttpResponse<String> updateOneMenuItem(@PathVariable("id") String id, 
			@RequestBody MenuItem menuItem) {
		try {
			menuItem.setId(id);
			menuItemService.updateMenuItem(menuItem);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			
			return getErrorStatus(ex);
		}
		return getOKStatus();
	}
}
