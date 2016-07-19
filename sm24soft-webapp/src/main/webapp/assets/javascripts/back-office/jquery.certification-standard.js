(function($) {
	//jQuery DOM Ready
	$(function() {
		tinymce.init({
			selector : "textarea",
			theme : "modern",
			width : 675,
			height : 300,
			plugins : [
					"searchreplace wordcount visualblocks visualchars nonbreaking spellchecker",
					"table contextmenu directionality emoticons paste textcolor" ],
			relative_urls : false,
			browser_spellcheck : true,
			image_advtab : true,
			toolbar1 : "undo redo | fontselect | fontsizeselect | bold italic underline | forecolor backcolor | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent"
		});
		
		var options = {
			rules: {
				name: {
					required: true
				},
				description: {
					required: true
				}
			},
			messages: {
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
				
				var isUpdateForm = $(form).hasClass("form-update-certification");
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
							location.href = Util.getRealPath("/admin/certification");
						}
					},
					error: function(e) {
						$.log(e.message);
						
						if (isUpdateForm) {
							Util.showMessageDialog(".fails-update-certification-dialog");
						} else {
							Util.showMessageDialog(".fails-create-certification-dialog");
						}
					}
				});
				return false;
			}
		};
		
		$("form.form-create-certification").validate(options);
		$("form.form-update-certification").validate(options);
		
		$("form.form-search-certification").on("click", "button", function() {
			$(this).closest("form").submit();
		});
		
		$("form").on("click", "a", function(e) {
			if ($(this).hasClass("btn-cancel")) {
				$("form")[0].reset();
			} else {
				$("form").submit();
			}
		});
		
		$("table.table-certification-standard").on("click", "a", function(e) {
			var itemId = $(this).attr("data-id");
			var table = $(this).closest("table.table-certification-standard");
			
			// mark current is selecting menu item
			table.attr("data-selected-id", itemId);
			
			if ($(this).hasClass("btn-edit")) {
				location.href = Util.getRealPath("/admin/certification/") + itemId;
			} else {
				Util.showMessageDialog(".confirm-delete-certification-dialog");
			}
		});
		
		$("div.confirm-delete-certification-dialog").on("click", "button", function(e) {
			var $this = $(this);
			if ($this.hasClass("btn-agreement")) {
				Util.hideMessageDialog(".confirm-delete-certification-dialog");
				
				var itemId = $("table.table-certification-standard").attr("data-selected-id");
				var action = Util.getRealPath("/admin/certification/") + itemId;
				
				$.ajax({
					url: action,
					type: 'DELETE',
					dataType: 'json',
					success: function(data) {
						if (data.status == 200) {
							location.href = Util.getRealPath("/admin/certification");
						}
					},
					error: function(e) {
						$.log(e.message);
						Util.showMessageDialog(".fails-delete-certification-dialog");
					}
				});
			}
		});
	});
	
})(window.jQuery);