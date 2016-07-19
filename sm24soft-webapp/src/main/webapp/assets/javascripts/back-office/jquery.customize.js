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
			return this.getContextPath() + ('/' == url.charAt(0) ? url : this.addSlashToUrl(url));
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
			$(clazz).modal('show');
		},
		
		hideMessageDialog: function(clazz) {
			$(clazz).modal('hide');
		},
		
		resetSelectBox: function (selectorClazzOrId) {
			$(selectorClazzOrId).html("<option value=\"\"></option>");
		}
	};
	window.Util = namespace;
	
	// jQuery DOM Ready
	$(function() {
		
	});
	
	// #############################################################
	// Private methods
	// #############################################################
	
})(window.jQuery);