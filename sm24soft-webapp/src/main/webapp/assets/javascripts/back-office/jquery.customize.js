(function($) {
	var namespace = {
		/**
		 * Get the application"s context path
		 * 
		 * @returns
		 */
		getContextPath: function() {
			return $("meta[name=contextPath]").attr("content");
		},

		/**
		 * Get relative Url
		 * 
		 * @param url
		 * @returns {String}
		 */
		getRealPath : function(url) {
			if (!url) {
				return this.getContextPath();
			}
			return this.getContextPath()
					+ ('/' == url.charAt(0) ? url : this.addSlashToUrl(url));
		},

		/**
		 * Add slash character at to first position of Url string
		 * 
		 * @param url
		 * @returns
		 */
		addSlashToUrl: function(url) {
			return "/" + url;
		},

		/**
		 * Redirect current Url to other Url
		 * 
		 * @param url
		 */
		redirectUrl: function(url) {
			location.href = this.getRealPath(url);
		},
		
		/**
		 * The JSON.stringify() method converts a JavaScript value to a JSON string, optionally 
		 * replacing values if a replacer function is specified, or optionally including only the 
		 * specified properties if a replacer array is specified.
		 * 
		 * @param value
		 * 		The value to convert to a JSON string.
		 */
		toJSONString: function(value) {
			return JSON.stringify(value);
		},
		
		showMessageDialog: function(clazz) {
			$(document).find(clazz).modal('show');
		},
		
		hideMessageDialog: function(clazz) {
			$(document).find(clazz).modal('hide');
		},
		
		addClassToNavigation: function(obj, param) {
			var item = $(obj).attr("class")
								.replace("active", "")
								.replace("current-page", "");
			var path = $(obj).attr("data-path");
			addClassToSelectedItem(path, item, param);
		}
	};
	window.Util = namespace;
	
	// jQuery DOM Ready
	$(function() {
		
	});
	
	// #############################################################
	// Private methods
	// #############################################################
	
	// Default is not click
	var isClickedOnNavigation = false;
	
	function addClassToSelectedItem(item, itemClazz, requestParam) {
		// if the child navigation already done by click
		// then do not need to trigger parent navigation one more time
		if (isClickedOnNavigation && requestParam === "act_menu") {
			return false;
		}
		
		if (requestParam === "act_child_menu") {
			isClickedOnNavigation = true;
		}
		
		var action = Util.getRealPath("/admin?");
		action += requestParam;
		action += "=";
		action += itemClazz;
		
		$.log(action);
		$.ajax({
			url: action,
			type: "PUT",
			dataType: "json",
			success: function(data) {
				if (data.status == 200) {
					Util.redirectUrl("/admin/" + item);
				}
			},
			error: function(e) {
				$.log(e.message);
			}
		});
	}
})(window.jQuery);