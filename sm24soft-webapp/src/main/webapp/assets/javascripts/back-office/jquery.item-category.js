(function($) {
	var isUpdateForm = false;
	//jQuery DOM Ready
	$(function() {
		isUpdateForm = $(".form-main").hasClass("form-update");
		var failsClazz = function() {
			return isUpdateForm ? FAILS_UPDATE_CLASS : FAILS_CREATE_CLASS;
		}();
		
		$("form.form-search").on("click", "button", function() {
			$(this).closest("form").submit();
		});
		
		var options = {
			rules: {
				"menuItem[id]": {
					required: true
				},
				"supplier[id]": {
					required: true
				},
				name: {
					required: true
				},
				description: {
					required: true
				}
			},
			messages: {
				"menuItem[id]": {
					required: REQUIRED_MESSAGE
				},
				"supplier[id]": {
					required: REQUIRED_MESSAGE
				},
				name: {
					required: REQUIRED_MESSAGE
				},
				description: {
					required: REQUIRED_MESSAGE
				}
			},
			submitHandler: function(form) {
				// it will save the contents in all editors to their bound textareas
				tinymce.triggerSave();
				
				var jSONData = Util.toJSONString($(form).serializeJSON());
				var action = $(form).attr("action");

				$.ajax({
					url: action,
					type: isUpdateForm ? "PUT" : "POST",
					contentType: APPLICATION_JSON,
					dataType: "json",
					data: jSONData,
					success: function(data) {
						if (data.status == 200) {
							location.href = Util.getRealPath("/admin/item-category");
						} else {
							Util.showMessageDialog(failsClazz);
						}
					},
					error: function(jqXHR, textStatus, errorThrown) {
						$.log(jqXHR, textStatus, errorThrown);
						
						if (jqXHR.status && jqXHR.status === 401) {
							location.reload(true);
						} else {
							Util.showMessageDialog(failsClazz);
						}
					}
				});
				return false;
			}
		};
		
		$("form.form-main").validate(options);
		$("form").on("click", "a", function(e) {
			if ($(this).hasClass("btn-cancel")) {
				$("form")[0].reset();
			} else {
				$("form").submit();
			}
		});
		
		$("table.table-item").on("click", "a", function(e) {
			var $this = $(this);
			var itemId = $this.attr("data-id");
			var table = $this.closest("table.table-item");
			
			// mark current is selecting menu item
			table.attr("data-selected-id", itemId);
			
			if ($this.hasClass("btn-edit")) {
				location.href = Util.getRealPath("/admin/item-category/") + itemId;
			} else {
				Util.showMessageDialog(FAILS_DELETE_CLASS);
			}
		});
		
		$("div.confirm-delete-dialog").on("click", "button", function(e) {
			var $this = $(this);
			if ($this.hasClass("btn-agreement")) {
				Util.hideMessageDialog(FAILS_DELETE_CLASS);
				
				var itemId = $("table.table-item").attr("data-selected-id");
				var action = Util.getRealPath("/admin/item-category/") + itemId;
				
				$.ajax({
					url: action,
					type: "DELETE",
					dataType: "json",
					success: function(data) {
						if (data.status == 200) {
							location.href = Util.getRealPath("/admin/item-category");
						} else {
							Util.showMessageDialog(FAILS_DELETE_CLASS);
						}
					},
					error: function(jqXHR, textStatus, errorThrown) {
						$.log(jqXHR, textStatus, errorThrown);
						
						if (jqXHR.status && jqXHR.status === 401) {
							location.reload(true);
						} else {
							Util.showMessageDialog(FAILS_DELETE_CLASS);
						}
					}
				});
			}
		});
	});
})(window.jQuery);