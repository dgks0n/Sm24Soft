(function($) {	
	var isUpdateForm = false; // default is create form
	
	//jQuery DOM Ready
	$(function() {
		isUpdateForm = $(".form-main").hasClass("form-update");
		var labelFinish = function() {
			return isUpdateForm ? "Cập nhật" : "Tạo mới";
		}();
		
		$("#wizard").smartWizard({
			labelNext: "Bỏ qua",
			labelPrevious: "Quay lại",
			labelFinish: labelFinish,
			onLeaveStep: onLeaveStepCallback,
			onFinish: onFinishCallback
		});
		
		$(".buttonNext").addClass("btn btn-default");
		$(".buttonPrevious").addClass("btn btn-default");
		$(".buttonFinish").addClass("btn btn-default");
		
		$("select#supplier-name").on("change", function() {
			loadCategories($(this).val());
		});
		
		$("#step_no2").on("change", "input", function() {
			var $this = $(this);
			
			uploadPreviewImage($this, $this.attr("data-id"), $this.attr("data-field-id"));
		});
		
		var options = {
			rules : {
				"supplier[id]" : {
					required : true
				},
				"itemCategory[id]" : {
					required : true
				},
				shortName : {
					required : true
				},
				salePrice : {
					required : true
				},
				totalWeight : {
					required : true
				},
				weightOfOneBox: {
					required: true,
					min: 0
				},
				discount: {
					required: true,
					min: 0
				}
			},
			messages : {
				"supplier[id]" : {
					required : "Thông tin bắt buộc"
				},
				"itemCategory[id]" : {
					required : "Thông tin bắt buộc"
				},
				shortName : {
					required : "Thông tin bắt buộc"
				},
				salePrice : {
					required : "Thông tin bắt buộc"
				},
				totalWeight : {
					required : "Thông tin bắt buộc"
				},
				weightOfOneBox: {
					required: "Thông tin bắt buộc",
					min: "Khối lượng đóng gói nhỏ nhất: 0"
				},
				discount: {
					required: "Thông tin bắt buộc",
					min: "Tỷ lệ khấu trừ nhỏ nhất: 0"
				}
			}
		};
		
		// form validation
		$("form.form-main").validate(options);
		
		// only validate for upload item's image from
		// if item is not created
		if (isUpdateForm) {
			$("form.form-upload").validate({
				rules: {
					previewImageUrl1: {
						required: true
					}
				},
				messages: {
					previewImageUrl1: {
						required: "Thông tin bắt buộc"
					}
				}
			});
		}
		
		// from submit
		$("form.form-main").submit(function(e) {
			e.preventDefault();
			
			var $this = $(this);
			var action = $this.attr("action");
			var jSONData = Util.toJSONString($this.serializeJSON());
			
			$.ajax({
				url: action,
				type: isUpdateForm ? "PUT" : "POST",
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				data: jSONData,
				success: function(data) {
					if (data.status == 200) {
						location.href = Util.getRealPath("/admin/item");
					} else {
						Util.showMessageDialog(isUpdateForm ? ".fails-update-dialog" : ".fails-create-dialog");
					}
				},
				error: function(jqXHR, textStatus, errorThrown) {
					$.log(jqXHR, textStatus, errorThrown);
					
					if (jqXHR.status && jqXHR.status === 401) {
						location.reload(true);
					} else {
						Util.showMessageDialog(isUpdateForm ? ".fails-update-dialog" : ".fails-create-dialog");
					}
				}
			});
		});
		
		$("form.form-search").on("click", "button", function() {
			$(this).closest("form").submit();
		});
		
		$("table.table-item").on("click", "a", function(e) {
			var $this = $(this);
			
			var itemId = $this.attr("data-id");
			var table = $this.closest("table.table-item");
			
			// mark current is selecting menu item
			table.attr("data-selected-id", itemId);
			
			if ($this.hasClass("btn-edit")) {
				location.href = Util.getRealPath("/admin/item/") + itemId;
			} else if ($this.hasClass("btn-copy")) {
				location.href = Util.getRealPath("/admin/item/copy-item/") + itemId;
			} else {
				Util.showMessageDialog(".confirm-delete-dialog");
			}
		});
		
		$("div.confirm-delete-dialog").on("click", "button", function(e) {
			var $this = $(this);
			if ($this.hasClass("btn-agreement")) {
				Util.hideMessageDialog(".confirm-delete-dialog");
				
				var itemId = $("table.table-item").attr("data-selected-id");
				var action = Util.getRealPath("/admin/item/") + itemId;
				
				$.ajax({
					url: action,
					type: 'DELETE',
					dataType: 'json',
					success: function(data) {
						if (data.status == 200) {
							location.href = Util.getRealPath("/admin/item");
						} else {
							Util.showMessageDialog(".fails-delete-dialog");
						}
					},
					error: function(jqXHR, textStatus, errorThrown) {
						$.log(jqXHR, textStatus, errorThrown);
						
						if (jqXHR.status && jqXHR.status === 401) {
							location.reload(true);
						} else {
							Util.showMessageDialog(".fails-delete-dialog");
						}
					}
				});
			}
		});
	});
	
	function onLeaveStepCallback(obj, context) {
		$.log("Leaving step " + context.fromStep + " to go to step " + context.toStep);
		
		return validateSteps(context.fromStep);
	}
	
	function onFinishCallback(objs, context) {
		if (!isUpdateForm) {
			var isStepValid = $("form.form-upload").valid();
			if (isStepValid == false) {
				return false;
			}
		}
		$("form.form-main").trigger("submit");
	}
	
	function validateSteps(stepnumber) {
        var isStepValid = true;
        if (stepnumber == 1) {
        	isStepValid = $("form.form-main").valid();
        } else if (stepnumber == 2 && isUpdateForm == false) {
        	isStepValid = $("form.form-upload").valid();
        }
        return isStepValid;
    }
	
	function loadCategories(supplierId) {
		var action = Util.getRealPath("/admin/supplier/item-categories?supplierId=");
		action += supplierId;
		
		$.ajax({
			url: action,
			type: 'GET',
			dataType: 'json',
			success: function(data) {
				if (data.status == 200) {
					$("select#item-category").html(data.result);
				} else {
					Util.resetSelectBox("select#item-category");
					Util.showMessageDialog(".fails-load-category-dialog");
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				$.log(jqXHR, textStatus, errorThrown);
				
				if (jqXHR.status && jqXHR.status === 401) {
					location.reload(true);
				} else {
					Util.resetSelectBox("select#item-category");
					Util.showMessageDialog(".fails-load-category-dialog");
				}
			}
		});
	}
	
	function uploadPreviewImage(obj, imageId, imageFieldId) {
		// Starting upload
		var action = Util.getRealPath("/admin/item/upload-preview-image");
		var imageUrl = obj[0].files[0];
		var formData = new FormData();
		formData.append("file", imageUrl);
		formData.append("imageId", imageId);
		formData.append("imageFieldId", imageFieldId);
		
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
					$("input#" + imageFieldId).val(data.result);
				} else {
					Util.showMessageDialog(".fails-upload-dialog");
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				$.log(jqXHR, textStatus, errorThrown);
				
				if (jqXHR.status && jqXHR.status === 401) {
					location.reload(true);
				} else {
					Util.showMessageDialog(".fails-upload-dialog");
				}
			}
		});
	}
})(window.jQuery);