var AccountValidate = function () {

	var handleLogin = function(lang) {
		$('.login-form').validate({
			errorElement: 'span', //default input error message container
			errorClass: 'help-block', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			rules: {
				username: {
					required: true
				},
				password: {
					required: true
				},
				remember: {
					required: false
				}
			},

			messages: {
				username: {
					required: lang.username
				},
				password: {
					required: lang.password
				}
			},

			invalidHandler: function (event, validator) { //display error alert on form submit   
				$('.alert-error', $('.login-form')).show();
			},

			highlight: function (element) { // hightlight error inputs
				$(element)
					.closest('.form-group').addClass('has-error'); // set error class to the control group
			},

			success: function (label) {
				label.closest('.form-group').removeClass('has-error');
				label.remove();
			},

			errorPlacement: function (error, element) {
				error.insertAfter(element.closest('.input-icon'));
			},

			submitHandler: function (form) {
				form.submit();
			}
		});

		$('.login-form input').keypress(function (e) {
			if (e.which == 13) {
				if ($('.login-form').validate().form()) {
					$('.login-form').submit();
				}
				return false;
			}
		});
	};

	var handleForgetPassword = function () {
		$('.forget-form').validate({
	            errorElement: 'span', //default input error message container
	            errorClass: 'help-block', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            ignore: "",
	            rules: {
	                email: {
	                    required: true,
	                    email: true
	                }
	            },

	            messages: {
	                email: {
	                    required: "Email is required."
	                }
	            },

	            invalidHandler: function (event, validator) { //display error alert on form submit   

	            },

	            highlight: function (element) { // hightlight error inputs
	                $(element)
	                    .closest('.form-group').addClass('has-error'); // set error class to the control group
	            },

	            success: function (label) {
	                label.closest('.form-group').removeClass('has-error');
	                label.remove();
	            },

	            errorPlacement: function (error, element) {
	                error.insertAfter(element.closest('.input-icon'));
	            },

	            submitHandler: function (form) {
	                form.submit();
	            }
	        });

	        $('.forget-form input').keypress(function (e) {
	            if (e.which == 13) {
	                if ($('.forget-form').validate().form()) {
	                    $('.forget-form').submit();
	                }
	                return false;
	            }
	        });

	        jQuery('#forget-password').click(function () {
	            jQuery('.login-form').hide();
	            jQuery('.forget-form').show();
	        });

	        jQuery('#back-btn').click(function () {
	            jQuery('.login-form').show();
	            jQuery('.forget-form').hide();
	        });

	};

	var handleRegister = function () {
         	$('.register-form').validate({
	            errorElement: 'span', //default input error message container
	            errorClass: 'help-block', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            ignore: "",
	            rules: {
	                
	                name: {
	                    required: true
	                },
	                email: {
	                    required: true,
	                    email: true
	                },
	                username: {
	                    required: true
	                },
	                password: {
	                    required: true,
			    minlength:8
	                },
	                confirmPassword: {
	                    equalTo: "#password"
	                },

	                agreement: {
	                    required: true
	                }
	            },

	            messages: { // custom messages for radio buttons and checkboxes
			name: {
				required: "请输入姓名。"
			},
			email: {
				required: "请输入邮箱。",
				email: "请输入正确的邮箱地址。",
			},
			username: {
				required: "请输入用户名。"
			},
			password: {
				required: "请输入密码。",
				minlength: jQuery.validator.format("密码长度不能小于{0}位。"),
			},
			confirmPassword: {
				required: "请再次输入密码。",
				equalTo: "两次输入不一致,请重新输入。",
			},
	                agreement: {
	                    required: "请先同意注册协议和隐私政策。"
	                }
	            },

	            invalidHandler: function (event, validator) { //display error alert on form submit   

	            },

	            highlight: function (element) { // hightlight error inputs
	                $(element)
	                    .closest('.form-group').addClass('has-error'); // set error class to the control group
	            },

	            success: function (label) {
	                label.closest('.form-group').removeClass('has-error');
	                label.remove();
	            },

	            errorPlacement: function (error, element) {
	                if (element.attr("name") == "agreement") { // insert checkbox errors after the container                  
	                    error.insertAfter($('#register_agreement_error'));
	                } else if (element.closest('.input-icon').size() === 1) {
	                    error.insertAfter(element.closest('.input-icon'));
	                } else {
	                	error.insertAfter(element);
	                }
	            },

	            submitHandler: function (form) {
	                form.submit();
	            }
	        });

		$('.register-form input').keypress(function (e) {
	            if (e.which == 13) {
	                if ($('.register-form').validate().form()) {
	                    $('.register-form').submit();
	                }
	                return false;
	            }
	        });

	        /*jQuery('#register-btn').click(function () {
	            jQuery('.login-form').hide();
	            jQuery('.register-form').show();
	        });

	        jQuery('#register-back-btn').click(function () {
	            jQuery('.login-form').show();
	            jQuery('.register-form').hide();
	        });*/
	};
    
    return {
        //main function to initiate the module
        handleLogin: function (lang) {	
            handleLogin(lang);   
        },

	handleForgetPassword: function () {
            handleForgetPassword();        
        },

	handleRegister: function () {
            handleRegister();               
        }
    };

}();
