(function($) {
	//Default is not upload logo yet
	var isUploadedLogo = false;
	
	//jQuery DOM Ready
	$(function() {
		$("#create-new-wizard").smartWizard({
			labelNext: "Bỏ qua",
			labelPrevious: "Quay lại",
			labelFinish: "Tạo mới",
			onLeaveStep: leaveAStepCallback,
			onFinish: onFinishCallback
		});
		
		$("#update-wizard").smartWizard({
			labelNext: "Bỏ qua",
			labelPrevious: "Quay lại",
			labelFinish: "Cập nhật",
			onLeaveStep: leaveAStepCallback,
			onFinish: onFinishCallback
		});
		
		$(".buttonNext").addClass("btn btn-default");
		$(".buttonPrevious").addClass("btn btn-default");
		$(".buttonFinish").addClass("btn btn-default");
		
		// default validate option
		var options = {
			rules: {
				companyName: {
					required: true
				},
				companyTradingName: {
					required: true
				},
				email: {
					required: true,
					email: true
				},
				telephoneNumber1: {
					required: true,
					digits: true,
					minlength: 9
				},
				address1: {
					required: true,
					minlength: 20
				},
				description: {
					required: true
				}
			},
			messages: {
				companyName: {
					required: "Thông tin bắt buộc"
				},
				companyTradingName: {
					required: "Thông tin bắt buộc"
				},
				email: {
					required: "Thông tin bắt buộc",
					email: "Vui lòng nhập địa chỉ email hợp lệ"
				},
				telephoneNumber1: {
					required: "Thông tin bắt buộc",
					digits: "Vui lòng chỉ nhập chữ số",
					minlength: "Số điện thoại không đúng. Ít nhất có 9 chữ số."
				},
				address1: {
					required: "Thông tin bắt buộc",
					minlength: "Địa chỉ không hợp lệ. Ít nhất có 20 ký tự."
				},
				description: {
					required: "Thông tin bắt buộc"
				}
			}
		};
		
		// form validation
		$("form").validate(options);
		$("form.form-create-supplier input[name=logoUrl]").rules("add", {
			required: true,
			messages: {
				required: "Thông tin bắt buộc"
			}
		});
		
		$("form").submit(function(event) {
			event.preventDefault();
			
			var action = $(this).attr("action");
			var isUpdateForm = $(this).hasClass("form-update-supplier");
			// evaluate the form using generic validating
			var jSONData = Util.toJSONString($(this).serializeJSON());
			
			$.log(jSONData);
			$.ajax({
				url: action,
				type: isUpdateForm ? "PUT" : "POST",
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				data: jSONData,
				success: function(data) {
					if (data.status == 200) {
						location.href = Util.getRealPath("/admin/supplier");
					}
				},
				error: function(e) {
					$.log(e.message);
					if (isUpdateForm) {
						Util.showMessageDialog(".fails-update-supplier-dialog");
					} else {
						Util.showMessageDialog(".fails-create-supplier-dialog");
					}
				}
			});
		});
		
		$("table.table-supplier").on("click", "a", function(e) {
			var itemId = $(this).attr("data-id"),
				table = $(this).closest("table.table-supplier");
			
			// mark current is selecting menu item
			table.attr("data-selected-id", itemId);
			
			if ($(this).hasClass("btn-edit")) {
				location.href = Util.getRealPath("/admin/supplier/") + itemId;
			} else {
				Util.showMessageDialog(".confirm-delete-supplier-dialog");
			}
		});
		
		$("div.confirm-delete-supplier-dialog").on("click", "button", function(e) {
			var $this = $(this);
			if ($this.hasClass("btn-agreement")) {
				Util.hideMessageDialog(".confirm-delete-supplier-dialog");
				
				var selectedItemId = $("table.table-supplier").attr("data-selected-id");
				var action = Util.getRealPath("/admin/supplier/");
				action += selectedItemId;
				
				$.ajax({
					url: action,
					type: "DELETE",
					dataType: "json",
					success: function(data) {
						if (data.status == 200) {
							location.href = Util.getRealPath("/admin/supplier");
						}
					},
					error: function(e) {
						$.log(e.message);
						Util.showMessageDialog(".fails-delete-supplier-dialog");
					}
				});
			}
		});
		
		$("#logoUrl").on("change", function(e) {
			var changedSupplierEmail = $("#email").val();
			if (changedSupplierEmail) {
				$.log("Uploading logo for the email: " + changedSupplierEmail);
				
				var logoFieldId = $(this).attr("data-id");
				uploadSupplierLogo($(this), changedSupplierEmail, logoFieldId);
			} else {
				$.log("Current email address is invalid: " + changedSupplierEmail);
				
				//Mark it still has not been uploaded
				isUploadedLogo = false;
				Util.showMessageDialog(".fails-upload-logo-dialog");
			}
		});
		
		$("#step-image-of-operations input[name=operationImageUrl]").on("change", function(e) {
			var imageFieldId = $(this).attr("data-id");
			uploadSupplierImages($(this), $("#email").val(), imageFieldId);
		});
	});
	
	function uploadSupplierLogo(obj, emailAddress, imageFieldId) {
		// Starting upload
		var action = Util.getRealPath("/admin/supplier/upload-logo?email=");
		action += emailAddress;
		
		$.log(action);
		
		var logoUrl = obj[0].files[0];
		var formData = new FormData();
		formData.append("file", logoUrl);
		formData.append("imageFieldId", imageFieldId);
		
		$.log(formData);
		$.ajax({
			type: "post",
			url: action,
			data: formData,
			cache: false,
			contentType: false,
			processData: false,
			dataType: "json",
			success: function(data, textStatus, jqXHR) {
				$.log(data, textStatus, jqXHR);
				//Mark it has been uploaded
				isUploadedLogo = true;
			},
			error: function(jqXHR, textStatus, errorThrown) {
				$.log(jqXHR, textStatus, errorThrown);
				//Mark it still has not been uploaded
				isUploadedLogo = false;
				
				Util.showMessageDialog(".fails-upload-logo-dialog");
			}
		});
	}
	
	function uploadSupplierImages(obj, emailAddress, imageFieldId) {
		// Starting upload
		var action = Util.getRealPath("/admin/supplier/upload-image?email=");
		action += emailAddress;
		
		$.log(action);
		
		var imageUrl = obj[0].files[0];
		var formData = new FormData();
		formData.append("file", imageUrl);
		formData.append("imageFieldId", imageFieldId);
		
		$.log(formData);
		$.ajax({
			type: "post",
			url: action,
			data: formData,
			cache: false,
			contentType: false,
			processData: false,
			dataType: "json",
			success: function(data, textStatus, jqXHR) {
				$.log(data, textStatus, jqXHR);
			},
			error: function(jqXHR, textStatus, errorThrown) {
				$.log(jqXHR, textStatus, errorThrown);
				// fails message
				Util.showMessageDialog(".fails-upload-logo-dialog");
			}
		});
	}
	
	function leaveAStepCallback(obj, context) {
		$.log("Leaving step " + context.fromStep + " to go to step " + context.toStep);
		// return false to stay on step and true to continue navigation
		return validateSteps(context.fromStep); 
	}
	
	function onFinishCallback(objs, context) {
		// Trigger submit form
		$("form").trigger("submit");
	}
	
	function validateSteps(stepnumber) {
        var isStepValid = true;
        // validate step 1
        if (stepnumber == 1) {
        	var form = $("form");
            // set isStepValid = false if has errors
        	isStepValid = form.valid();
        	
        	// if form is create new form and the logo still has not
			// been uploaded then we will have to upload before go
			// to next step
        	if (form.hasClass("form-create-supplier") && isStepValid && !isUploadedLogo) {
        		var changedSupplierEmail = $("#email").val();
        		uploadSupplierLogo($("#logoUrl"), changedSupplierEmail);
        	}
        }
        return isStepValid;
    }
	
})(window.jQuery);