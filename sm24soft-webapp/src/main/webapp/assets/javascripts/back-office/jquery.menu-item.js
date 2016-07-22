(function($) {
	var isUpdateForm = false;
	
	//jQuery DOM Ready
	$(function() {
		isUpdateForm = $(".form-main").hasClass("form-update");
		
		$("form").on("click", "a", function(e) {
			var $this = $(this);
			var form = $this.closest("form");
			if ($this.hasClass("btn-cancel")) {
				form[0].reset();
			} else {
				form.submit();
			}
		});
		
		$("form").validate({
			rules: {
				fullNameOfMenuItem: "required",
				description: "required"
			},
			messages: {
				fullNameOfMenuItem: REQUIRED_MESSAGE,
				description: REQUIRED_MESSAGE
			},
			submitHandler: function(form) {
				var action = $(form).attr("action");
				
				// evaluate the form using generic validating
				var jSONData = Util.toJSONString($(form).serializeJSON());
				
				$.log(jSONData);
				$.ajax({
					url: action,
					type: isUpdateForm ? "PUT" : "POST",
					contentType: APPLICATION_JSON,
					dataType: "json",
					data: jSONData,
					success: function(data) {
						if (data.status == 200) {
							location.href = Util.getRealPath("/admin/menu-item");
						} else {
							Util.showMessageDialog(isUpdateForm ? FAILS_UPDATE_CLASS : FAILS_CREATE_CLASS);
						}
					},
					error: function(jqXHR, textStatus, errorThrown) {
						$.log(jqXHR, textStatus, errorThrown);
						
						if (jqXHR.status && jqXHR.status === 401) {
							location.reload(true);
						} else {
							Util.showMessageDialog(isUpdateForm ? FAILS_UPDATE_CLASS : FAILS_CREATE_CLASS);
						}
					}
				});
				// required to block normal submit since you used ajax
				return false;
			}
		});
		
		$("table.table-menu").on("click", "a", function(e) {
			var $this = $(this);
			var itemId = $this.attr("data-id");
			var table = $this.closest("table.table-menu");
			
			// mark current is selecting menu item
			table.attr("data-selected-id", itemId);
			
			if ($this.hasClass("btn-edit")) {
				location.href = Util.getRealPath("/admin/menu-item/") + itemId;
			} else {
				Util.showMessageDialog(FAILS_DELETE_CLASS);
			}
		});
		
		$("div.confirm-delete-dialog").on("click", "button", function(e) {
			var $this = $(this);
			if ($this.hasClass("btn-agreement")) {
				Util.hideMessageDialog(FAILS_DELETE_CLASS);
				
				var itemId = $("table.table-menu").attr("data-selected-id");
				var action = Util.getRealPath("/admin/menu-item/") + itemId;
				
				$.ajax({
					url: action,
					type: "DELETE",
					dataType: "json",
					success: function(data) {
						if (data.status == 200) {
							location.href = Util.getRealPath("/admin/menu-item");
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