(function($) {
	//jQuery DOM Ready
	$(function() {
		$("form").on("click", "a", function(e) {
			var form = $(this).closest("form");
			if ($(this).hasClass("btn-cancel")) {
				form[0].reset();
			} else {
				$(this).submit();
			}
		});
		
		$("form").validate({
			rules: {
				fullNameOfMenuItem: "required",
				description: "required"
			},
			messages: {
				fullNameOfMenuItem: "Thông tin bắt buộc",
				description: "Thông tin bắt buộc"
			},
			submitHandler: function(form) {
				var action = $(form).attr("action");
				var isUpdateForm = $(form).hasClass("form-update-menu");
				// evaluate the form using generic validating
				var jSONData = Util.toJSONString($(form).serializeJSON());
				
				$.log(jSONData);
				$.ajax({
					url: action,
					type: isUpdateForm ? 'PUT' : 'POST',
					contentType: 'application/json; charset=utf-8',
					dataType: 'json',
					data: jSONData,
					success: function(data) {
						if (data.status == 200) {
							location.href = Util.getRealPath("/admin/menu-item");
						}
					},
					error: function(e) {
						$.log(e.message);
						if (isUpdateForm) {
							Util.showMessageDialog(".fails-update-menu-dialog");
						} else {
							Util.showMessageDialog(".fails-create-menu-dialog");
						}
					}
				});
				// required to block normal submit since you used ajax
				return false;
			}
		});
		
		$("table.table-menu-item").on("click", "a", function(e) {
			var _this = $(this);
			var itemId = _this.attr("data-id");
			var table = _this.closest("table.table-menu-item");
			
			// mark current is selecting menu item
			table.attr("data-selected-id", itemId);
			
			if (_this.hasClass("btn-edit")) {
				location.href = Util.getRealPath("/admin/menu-item/") + itemId;
			} else {
				Util.showMessageDialog(".confirm-delete-menu-dialog");
			}
		});
		
		$("div.confirm-delete-menu-dialog").on("click", "button", function(e) {
			var _this = $(this);
			if (_this.hasClass("btn-agreement")) {
				Util.hideMessageDialog(".confirm-delete-menu-dialog");
				
				var itemId = $("table.table-menu-item").attr("data-selected-id");
				var action = Util.getRealPath("/admin/menu-item/") + itemId;
				
				$.ajax({
					url: action,
					type: 'DELETE',
					dataType: 'json',
					success: function(data) {
						if (data.status == 200) {
							location.href = Util.getRealPath("/admin/menu-item");
						}
					},
					error: function(e) {
						$.log(e.message);
						Util.showMessageDialog(".fails-delete-menu-dialog");
					}
				});
			}
		});
	});
	
})(window.jQuery);