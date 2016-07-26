/**
 * Common Const variables of Sm24Soft 2016-20-07
 * 
 * Refer to:
 * https://developer.mozilla.org/en/docs/Web/JavaScript/Reference/Statements/const
 */
const REQUIRED_MESSAGE = "Thông tin bắt buộc",

INVALID_WEIGHT_MESSAGE = "Khối lượng đóng gói nhỏ nhất: 0",
INVALID_DISCOUNT_MESSAGE = "Tỷ lệ khấu trừ nhỏ nhất: 0",
INVALID_EMAIL_MESSAGE = "Vui lòng nhập địa chỉ email hợp lệ",
INVALID_DIGIT_MESSAGE = "Vui lòng chỉ nhập chữ số",
INVALID_TELEPHONE_LENGTH_MESSAGE = "Số điện thoại không đúng. Ít nhất có 9 chữ số.",
INVALID_ADDRESS_LENGTH_MESSAGE = "Địa chỉ không hợp lệ. Ít nhất có 20 ký tự.",

APPLICATION_JSON = "application/json; charset=utf-8",

FAILS_CREATE_CLASS = ".fails-create-dialog",
FAILS_UPDATE_CLASS = ".fails-update-dialog",
FAILS_DELETE_CLASS = ".fails-delete-dialog",
FAILT_UPLOAD_CLASS = ".fails-upload-dialog",

WIZARD_LABEL_NEXT = "Bỏ qua",
WIZARD_LABEL_PREVIOUS = "Quay lại",
WIZARD_LABEL_CREATE = "Tạo mới",
WIZARD_LABEL_UPDATE = "Cập nhật";

(function($) {
	var namespace = {
		/**
		 * Get the application"s context path
		 * 
		 * @returns
		 */
		getContextPath : function() {
			return $("meta[name=contextPath]").attr("content");
		},

		/**
		 * Get relative Url
		 * 
		 * @param url
		 * @returns {String}
		 */
		getRealPath : function(url) {
			if (!url) {
				return this.getContextPath();
			}
			return this.getContextPath()
					+ ("/" == url.charAt(0) ? url : this.addSlashToUrl(url));
		},

		/**
		 * Add slash character at to first position of Url string
		 * 
		 * @param url
		 * @returns
		 */
		addSlashToUrl : function(url) {
			return "/" + url;
		},

		/**
		 * Redirect current Url to other Url
		 * 
		 * @param url
		 */
		redirectUrl : function(url) {
			location.href = this.getRealPath(url);
		},

		/**
		 * The JSON.stringify() method converts a JavaScript value to a JSON
		 * string, optionally replacing values if a replacer function is
		 * specified, or optionally including only the specified properties if a
		 * replacer array is specified.
		 * 
		 * @param value
		 *            The value to convert to a JSON string.
		 */
		toJSONString : function(value) {
			return JSON.stringify(value);
		},

		showMessageDialog : function(clazz) {
			$(clazz).modal("show");
		},

		hideMessageDialog : function(clazz) {
			$(clazz).modal("hide");
		},

		resetSelectBox : function(selectorClazzOrId) {
			$(selectorClazzOrId).html("<option value=\"\"></option>");
		}
	};

	window.Util = namespace;
})(window.jQuery);