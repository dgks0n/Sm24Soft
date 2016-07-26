(function($) {
	var isUpdateForm = false;
	
	//jQuery DOM Ready
	$(function() {
		isUpdateForm = $(".form-main").hasClass("form-update");
		
		var failsClazz = function() {
			return isUpdateForm ? FAILS_UPDATE_CLASS : FAILS_CREATE_CLASS;
		}();
		
		var options = {
			rules: {
				advertiseImage: {
					required: isUpdateForm ? false : true
				}
			},
			messages: {
				advertiseImage: {
					required: REQUIRED_MESSAGE
				}
			},
			submitHandler: function(form) {
				var action = $(form).attr("action");
				// Create a new FormData object.
				var formData = new FormData();
				var fileSelect = document.getElementById("advertise-image");
				
				// Get the selected files from the input.
				var files = fileSelect.files;
				
				// Loop through each of the selected files.
				for (var i = 0; i < files.length; i++) {
					var file = files[i];
					
					// Check the file type.
					if (!file.type.match("image.*")) {
						continue;
					}
					
					// Add the file to the request.
					formData.append("files[" + i + "]", file, file.name);
				}
				formData.append("modelAttribute", "uploadForm");
				
				$.ajax({
					type: "POST",
					url: action,
					data: formData,
					cache: false,
					contentType: false,
					processData: false,
					dataType: "json",
					success: function(data, textStatus, jqXHR) {
						$.log(data, textStatus, jqXHR);
						
						if (data.status == 200) {
							location.href = Util.getRealPath("/admin/advertise-image");
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
			var $this = $(this);
			var form = $this.closest("form");
			if ($this.hasClass("btn-cancel")) {
				form[0].reset();
			} else {
				form.submit();
			}
		});
		
		$("table.table-advertise").on("click", "a", function(e) {
			var $this = $(this);
			var itemId = $this.attr("data-id");
			var table = $this.closest("table.table-advertise");
			
			// mark current is selecting menu item
			table.attr("data-selected-id", itemId);
			
			if ($this.hasClass("btn-edit")) {
				location.href = Util.getRealPath("/admin/advertise-image/") + itemId;
			} else {
				Util.showMessageDialog(".confirm-delete-dialog");
			}
		});
		
		$("div.confirm-delete-dialog").on("click", "button", function(e) {
			var $this = $(this);
			if ($this.hasClass("btn-agreement")) {
				Util.hideMessageDialog(FAILS_DELETE_CLASS);
				
				var itemId = $("table.table-advertise").attr("data-selected-id");
				var action = Util.getRealPath("/admin/advertise-image/") + itemId;
				
				$.ajax({
					url: action,
					type: "DELETE",
					dataType: "json",
					success: function(data) {
						if (data.status == 200) {
							location.href = Util.getRealPath("/admin/advertise-image");
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