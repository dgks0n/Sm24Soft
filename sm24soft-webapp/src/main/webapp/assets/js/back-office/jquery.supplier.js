(function($) {
	//jQuery DOM Ready
	$(function() {
		$("#wizard").smartWizard({
			labelNext: "Tiếp theo",
			labelPrevious: "Quay lại",
			labelFinish: "Kết thúc",
			
			// Call back
			onLeaveStep: leaveAStepCallback,
			onFinish: onFinishCallback
		});
		
		$(".buttonNext").addClass("btn btn-default");
		$(".buttonPrevious").addClass("btn btn-default");
		$(".buttonFinish").addClass("btn btn-default");
		
		// Form validation
		$("form.form-create-supplier").validate({
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
					digits: true
				},
				address1: {
					required: true
				}
			},
			messages: {
				companyName: {
					required: "Thông tin này là bắt buộc"
				},
				companyTradingName: {
					required: "Thông tin này là bắt buộc"
				},
				email: {
					required: "Thông tin này là bắt buộc",
					email: "Vui lòng nhập địa chỉ email hợp lệ"
				},
				telephoneNumber1: {
					required: "Thông tin này là bắt buộc",
					digits: "Vui lòng chỉ nhập chữ số"
				},
				address1: {
					required: "Thông tin này là bắt buộc"
				}
			}
		});
		
		$("form.form-create-supplier").submit(function(event) {
			event.preventDefault();
			var _this = $(this),
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
						location.href = Util.getRealPath("/admin/supplier");
					} else {
						Util.showMessageDialog(".fails-create-supplier-dialog");
					}
				},
				error: function(e) {
					$.log(e.message);
					Util.showMessageDialog(".fails-create-supplier-dialog");
				}
			});
		});
		
		$("table.table-supplier").on("click", "a", function(e) {
			var _this = $(this);
			var itemId = _this.attr("data-id");
			var table = _this.closest("table.table-supplier");
			
			// mark current is selecting menu item
			table.attr("data-selected-id", itemId);
			
			if (_this.hasClass("btn-edit")) {
				location.href = Util.getRealPath("/admin/supplier/update/") + itemId;
			} else {
				Util.showMessageDialog(".confirm-delete-supplier-dialog");
			}
		});
		
		$("div.confirm-delete-supplier-dialog").on("click", "button", function(e) {
			var _this = $(this);
			if (_this.hasClass("btn-agreement")) {
				var itemId = $("table.table-supplier").attr("data-selected-id");
				var action = Util.getRealPath("/admin/supplier/delete/") + itemId;
				
				$.ajax({
					url: action,
					type: 'POST',
					dataType: 'json',
					success: function(data) {
						if (data.status == 200) {
							location.href = Util.getRealPath("/admin/supplier");
						} else {
							Util.showMessageDialog(".fails-delete-supplier-dialog");
						}
					},
					error: function(e) {
						$.log(e.message);
						Util.showMessageDialog(".fails-delete-supplier-dialog");
					}
				});
			}
		});
	});
	
	function leaveAStepCallback(obj, context) {
		$.log("Leaving step " + context.fromStep + " to go to step " + context.toStep);
		
		// return false to stay on step and true to continue navigation
		return validateSteps(context.fromStep); 
	}
	
	function onFinishCallback(objs, context) {
		// Trigger submit form
		$("form.form-create-supplier").trigger("submit");
	}
	
	function validateSteps(stepnumber) {
        var isStepValid = true;
        // validate step 1
        if (stepnumber == 1) {
            // set isStepValid = false if has errors
        	isStepValid = $("form.form-create-supplier").valid();
        }
        return isStepValid;
    }
	
})(window.jQuery);