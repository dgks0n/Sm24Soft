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
		getRealPath: function(url) {
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
			$(document).find(clazz).modal('show');
		}
	};
	window.Util = namespace;
	
	// jQuery DOM Ready
	$(function() {
		$("ul.side-menu li").click(function() {
			var _this = $(this);
			addClassToSelectedItem(
					_this.attr("data-path"),
					_this.attr("class").replace("active", ""));
		});
	});
	
	function addClassToSelectedItem(item, itemClazz) {
		var url = Util.getRealPath("/admin/index?active_menu_item=");
		
		$.ajax({
			url: url + itemClazz,
			type: "POST",
			dataType: "json",
			success: function(data) {
				if (data.status == 200) {
					Util.redirectUrl("/admin/" + item);
				} else {
					console.log(data.error.message);
				}
			},
			error: function(e) {
				console.log(e.message);
			}
		});
	}
})(window.jQuery);