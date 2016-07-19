(function($) {
	//jQuery DOM Ready
	$(function() {
		$("form.form-search-group-item").on("click", "button", function() {
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
					required: "Thông tin bắt buộc"
				},
				"supplier[id]": {
					required: "Thông tin bắt buộc"
				},
				name: {
					required: "Thông tin bắt buộc"
				},
				description: {
					required: "Thông tin bắt buộc"
				}
			},
			submitHandler: function(form) {
				// it will save the contents in all editors to their bound textareas
				tinymce.triggerSave();
				
				var isUpdateForm = $(form).hasClass("form-update-group-item");
				var jSONData = Util.toJSONString($(form).serializeJSON());
				var action = $(form).attr("action");
				
				$.log(jSONData);
				$.ajax({
					url: action,
					type: isUpdateForm ? 'PUT' : 'POST',
					contentType: 'application/json; charset=utf-8',
					dataType: 'json',
					data: jSONData,
					success: function(data) {
						if (data.status == 200) {
							location.href = Util.getRealPath("/admin/item-category");
						}
					},
					error: function(e) {
						$.log(e.message);
						
						if (isUpdateForm) {
							Util.showMessageDialog(".fails-update-group-item-dialog");
						} else {
							Util.showMessageDialog(".fails-create-group-item-dialog");
						}
					}
				});
				return false;
			}
		};
		
		$("form.form-create-group-item").validate(options);
		$("form.form-update-group-item").validate(options);
		
		$("form").on("click", "a", function(e) {
			if ($(this).hasClass("btn-cancel")) {
				$("form")[0].reset();
			} else {
				$("form").submit();
			}
		});
		
		$("table.table-item-category").on("click", "a", function(e) {
			var itemId = $(this).attr("data-id");
			var table = $(this).closest("table.table-item-category");
			
			// mark current is selecting menu item
			table.attr("data-selected-id", itemId);
			
			if ($(this).hasClass("btn-edit")) {
				location.href = Util.getRealPath("/admin/item-category/") + itemId;
			} else {
				Util.showMessageDialog(".confirm-delete-item-category-dialog");
			}
		});
		
		$("div.confirm-delete-item-category-dialog").on("click", "button", function(e) {
			var $this = $(this);
			if ($this.hasClass("btn-agreement")) {
				Util.hideMessageDialog(".confirm-delete-item-category-dialog");
				
				var itemId = $("table.table-item-category").attr("data-selected-id");
				var action = Util.getRealPath("/admin/item-category/") + itemId;
				
				$.ajax({
					url: action,
					type: 'DELETE',
					dataType: 'json',
					success: function(data) {
						if (data.status == 200) {
							location.href = Util.getRealPath("/admin/item-category");
						}
					},
					error: function(e) {
						$.log(e.message);
						Util.showMessageDialog(".fails-delete-item-category-dialog");
					}
				});
			}
		});
	});
})(window.jQuery);