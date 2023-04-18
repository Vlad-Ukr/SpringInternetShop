jQuery(function ($) {
    $('#contactForm').on('submit', function (event) {
        if (validateForm()) {
            event.preventDefault();
        }
    });

    function validateForm() {
        var text_errors = $(".text-error");
        for (var i = 0; i < text_errors.length; i++) {
            text_errors[i].remove();
        }
        var el_firstName = $("#name");
        var firstNameRegex = /[A-ZА-Я][a-zа-я]{1,23}/g;
        var v_firstName = !el_firstName.val();
        if (!firstNameRegex.test(el_firstName.val())) {
            v_firstName = true;
            el_firstName.after('<span class="text-error for-firstName">Incorrect name </span>')
            $(".for-firstName").css({top: el_firstName.position().top + el_firstName.outerHeight() + 2});
        }
        $('#firstName').toggleClass('error', v_firstName);


 var regex =/^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?/i
        var el_email = $("#email");
        var v_email = !el_email.val();
		console.log(regex);
		console.log(el_email.val());
		console.log(regex.test(el_email.val()));
        if (v_email) {
            el_email.after('<span class="text-error for-email">E-mail is necessary to input</span>');
            $(".for-email").css({top: el_email.position().top + el_email.outerHeight() + 2});
        } else if (!regex.test(el_email)) {
            v_email = true;
            el_email.after('<span class="text-error for-email">E-mail is not correct(Ex= email@email.com).</span>');
            $(".for-email").css({top: el_email.position().top + el_email.outerHeight() + 2});
        }
        $("#emailInput").toggleClass('error', v_email);

        var el_password1 = $("#password");
        var el_password2 = $("#pass");
        var v_password1 = !el_password1.val();
        var v_password2 = !el_password2.val();
		var passwordRegex =  /^.*(?=.{8,})(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%&]).*$/i
		console.log(passwordRegex.test(el_password1.val()));
        if (el_password1.val() != el_password2.val()) {
            v_password1 = true;
            v_password2 = true;
            el_password1.after('<span class="text-error for-pass1">Password does not match!</span>');
            $(".for-pass1").css({top: el_password1.position().top + el_password1.outerHeight() + 2});
        } else if (el_password1.val().length < 6) {
            v_password1 = true;
            v_password2 = true;
            el_password1.after('<span class="text-error for-pass1">Password must be 6 or more characters</span>');
            $(".for-pass1").css({top: el_password1.position().top + el_password1.outerHeight() + 2});
        } else if (!passwordRegex.test(el_password1.val())) {
            v_password1 = true;
            v_password2 = true;
            el_password1.after('<span class="text-error for-pass1">Password should contains numbers,characters and special characters</span>');
            $(".for-pass1").css({top: el_password1.position().top + el_password1.outerHeight() + 2});
        }
        $("#password").toggleClass('error', v_password1);
        $("#pass").toggleClass('error', v_password2);
        return (v_firstName || v_password1 || v_password2);
    }

});

