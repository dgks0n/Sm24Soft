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
		
		$(".form-search-certification button.btn-search").on("click", function() {
			$(".form-search-certification").submit();
		});
		
		$("form").on("click", "a", function(e) {
			if ($(this).hasClass("btn-cancel")) {
				$("form")[0].reset();
			} else {
				$("form").submit();
			}
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
				
				var jSONData = Util.toJSONString($(form).serializeJSON());
				var action = $(form).attr("action");
				
				$.ajax({
					url: action,
					type: 'POST',
					contentType: 'application/json; charset=utf-8',
					dataType: 'json',
					data: jSONData,
					success: function(data) {
						if (data.status == 200) {
							location.href = Util.getRealPath("/admin/supplier/certification-standard");
						}
					},
					error: function(e) {
						$.log(e.message);
						Util.showMessageDialog(".fails-create-certification-dialog");
					}
				});
				return false;
			}
		};
		$("form").validate(options);
		
		$("table.table-certification-standard").on("click", "a", function(e) {
			var itemId = $(this).attr("data-id");
			var table = $(this).closest("table.table-menu-item");
			
			// mark current is selecting menu item
			table.attr("data-selected-id", itemId);
			
			if ($(this).hasClass("btn-edit")) {
				location.href = Util.getRealPath("/admin/supplier/certification-standard/") + itemId;
			} else {
				Util.showMessageDialog(".confirm-delete-menu-dialog");
			}
		});
	});
	
})(window.jQuery);