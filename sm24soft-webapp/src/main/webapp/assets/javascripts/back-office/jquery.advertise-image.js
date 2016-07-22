(function($) {
	var isUpdateForm = false;
	
	//jQuery DOM Ready
	$(function() {
		isUpdateForm = $(".form-main").hasClass("form-update");
		
		var options = {
			rules: {
				advertiseImage: {
					required: true
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
							Util.showMessageDialog(FAILS_CREATE_CLASS);
						}
					},
					error: function(jqXHR, textStatus, errorThrown) {
						$.log(jqXHR, textStatus, errorThrown);
						
						if (jqXHR.status && jqXHR.status === 401) {
							location.reload(true);
						} else {
							Util.showMessageDialog(FAILS_CREATE_CLASS);
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
	});
})(window.jQuery);