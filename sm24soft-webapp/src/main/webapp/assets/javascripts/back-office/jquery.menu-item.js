(function($) {
	//jQuery DOM Ready
	$(function() {
		var _fromCreateMenu = $(".form-create-menu");
		_fromCreateMenu.on("click", "a", function(e) {
			var _this = $(this),
				_form = _this.closest("form");
			if (_this.hasClass("btn-cancel")) {
				_form[0].reset();
			} else {
				_form.submit();
			}
		});
		
		_fromCreateMenu.validate({
			rules: {
				fullNameOfMenuItem: "required",
				fullPathOfMenuItem: "required"
			},
			messages: {
				fullNameOfMenuItem: "Thông tin bắt buộc",
				fullPathOfMenuItem: "Thông tin bắt buộc"
			},
			submitHandler: function(form) {
				var _this = $(form),
					action = _this.attr("action");
				
				// evaluate the form using generic validating
				var jSONData = Util.toJSONString(_this.serializeJSON());
				
				$.ajax({
					url: action,
					type: 'POST',
					contentType: 'application/json; charset=utf-8',
					dataType: 'json',
					data: jSONData,
					success: function(data) {
						if (data.status == 200) {
							location.href = Util.getRealPath("/admin/menu-item");
						} else {
							Util.showMessageDialog(".fails-create-menu-dialog");
						}
					},
					error: function(e) {
						$.log(e.message);
						Util.showMessageDialog(".fails-create-menu-dialog");
					}
				});
			
				// required to block normal submit since you used ajax
				return false;
			}
		});
		
		_fromCreateMenu.on("click", "a", function(e) {
			var _this = $(this),
				_form = _this.closest("form");
			if (_this.hasClass("btn-cancel")) {
				location.reload();
			} else {
				Util.showMessageDialog(".confirm-update-menu-dialog");
			}
		});
		
		$("div.confirm-update-menu-dialog").on("click", "button", function(e) {
			var _this = $(this),
				_form = $("form.form-update-menu");
			if (_this.hasClass("btn-agreement")) {
				// evaluate the form using generic validating
				var jSONData = Util.toJSONString(_form.serializeJSON());
				
				$.ajax({
					url: _form.attr("action"),
					type: 'POST',
					contentType: 'application/json; charset=utf-8',
					dataType: 'json',
					data: jSONData,
					success: function(data) {
						if (data.status == 200) {
							location.href = Util.getRealPath("/admin/menu-item");
						} else {
							Util.showMessageDialog(".fails-update-menu-dialog");
						}
					},
					error: function(e) {
						$.log(e.message);
						Util.showMessageDialog(".fails-update-menu-dialog");
					}
				});
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
				var itemId = $("table.table-menu-item").attr("data-selected-id");
				var action = Util.getRealPath("/admin/menu-item/delete/") + itemId;
				
				$.ajax({
					url: action,
					type: 'POST',
					dataType: 'json',
					success: function(data) {
						if (data.status == 200) {
							location.href = Util.getRealPath("/admin/menu-item");
						} else {
							Util.showMessageDialog(".fails-delete-menu-dialog");
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