(function($) {
	//Default is not upload logo yet
	var isUploadedLogo = false;
	var isUpdateForm = false;
	
	//jQuery DOM Ready
	$(function() {
		isUpdateForm = $(".form-main").hasClass("form-update");
		
		var labelFinish = function() {
			return isUpdateForm ? WIZARD_LABEL_UPDATE : WIZARD_LABEL_CREATE;
		}();
		
		var failsClazz = function() {
			return isUpdateForm ? FAILS_UPDATE_CLASS : FAILS_CREATE_CLASS;
		}(); 
		
		
		$("#wizard").smartWizard({
			labelNext: WIZARD_LABEL_NEXT,
			labelPrevious: WIZARD_LABEL_PREVIOUS,
			labelFinish: labelFinish,
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
					required: REQUIRED_MESSAGE
				},
				companyTradingName: {
					required: REQUIRED_MESSAGE
				},
				email: {
					required: REQUIRED_MESSAGE,
					email: INVALID_EMAIL_MESSAGE
				},
				telephoneNumber1: {
					required: REQUIRED_MESSAGE,
					digits: INVALID_DIGIT_MESSAGE,
					minlength: INVALID_TELEPHONE_LENGTH_MESSAGE
				},
				address1: {
					required: REQUIRED_MESSAGE,
					minlength: INVALID_ADDRESS_LENGTH_MESSAGE
				},
				description: {
					required: REQUIRED_MESSAGE
				}
			}
		};
		
		// form validation
		$("form").validate(options);
		$("form.form-create input[name=logoUrl]").rules("add", {
			required: true,
			messages: {
				required: REQUIRED_MESSAGE
			}
		});
		
		$("form").submit(function(event) {
			event.preventDefault();
			
			var action = $(this).attr("action");
			var jSONData = Util.toJSONString($(this).serializeJSON());
			
			$.ajax({
				url: action,
				type: isUpdateForm ? "PUT" : "POST",
				contentType: APPLICATION_JSON,
				dataType: "json",
				data: jSONData,
				success: function(data) {
					if (data.status == 200) {
						location.href = Util.getRealPath("/admin/supplier");
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
		});
		
		$("table.table-supplier").on("click", "a", function(e) {
			var itemId = $(this).attr("data-id");
			var table = $(this).closest("table.table-supplier");
			
			// mark current is selecting menu item
			table.attr("data-selected-id", itemId);
			
			if ($(this).hasClass("btn-edit")) {
				location.href = Util.getRealPath("/admin/supplier/") + itemId;
			} else {
				Util.showMessageDialog(".confirm-delete-dialog");
			}
		});
		
		$("div.confirm-delete-dialog").on("click", "button", function(e) {
			var $this = $(this);
			if ($this.hasClass("btn-agreement")) {
				Util.hideMessageDialog(FAILS_DELETE_CLASS);
				
				var selectedItemId = $("table.table-supplier").attr("data-selected-id");
				var action = Util.getRealPath("/admin/supplier/") + selectedItemId;
				
				$.ajax({
					url: action,
					type: "DELETE",
					dataType: "json",
					success: function(data) {
						if (data.status == 200) {
							location.href = Util.getRealPath("/admin/supplier");
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
		
		$("input[name=logoUrl]").on("change", function(e) {
			var changedSupplierEmail = $("#email").val();
			if (changedSupplierEmail) {
				$.log("Uploading logo for the email: " + changedSupplierEmail);
				
				var logoFieldId = $(this).attr("data-id");
				uploadSupplierLogo($(this), changedSupplierEmail, logoFieldId);
			} else {
				$.log("Current email address is invalid: " + changedSupplierEmail);
				
				//Mark it still has not been uploaded
				isUploadedLogo = false;
				Util.showMessageDialog(FAILT_UPLOAD_CLASS);
			}
		});
		
		$("input[name=operationImageUrl]").on("change", function(e) {
			var $this = $(this);
			var imageFieldId = $this.attr("data-id");
			
			uploadSupplierImages($this, $("#email").val(), imageFieldId);
		});
	});
	
	function uploadSupplierLogo(obj, emailAddress, imageFieldId) {
		var logoUrl = obj[0].files[0];
		var formData = new FormData();
		
		formData.append("file", logoUrl);
		formData.append("imageFieldId", imageFieldId);
		
		var action = Util.getRealPath("/admin/supplier/upload-logo?email=") + emailAddress;
		
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
				
				if (jqXHR.status && jqXHR.status === 401) {
					location.reload(true);
				} else {
					Util.showMessageDialog(FAILT_UPLOAD_CLASS);
				}
			}
		});
	}
	
	function uploadSupplierImages(obj, emailAddress, imageFieldId) {
		var imageUrl = obj[0].files[0];
		var formData = new FormData();
		
		formData.append("file", imageUrl);
		formData.append("imageFieldId", imageFieldId);
		
		var action = Util.getRealPath("/admin/supplier/upload-image?email=") + emailAddress;
		
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
				
				if (jqXHR.status && jqXHR.status === 401) {
					location.reload(true);
				} else {
					Util.showMessageDialog(FAILT_UPLOAD_CLASS);
				}
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
        	if (!isUpdateForm && isStepValid && !isUploadedLogo) {
        		var changedSupplierEmail = $("#email").val();
        		
        		uploadSupplierLogo($("#logoUrl"), changedSupplierEmail);
        	}
        }
        return isStepValid;
    }
	
})(window.jQuery);