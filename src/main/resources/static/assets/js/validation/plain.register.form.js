var plainRegisterForm = document.querySelector('#contactForm');
var password = plainRegisterForm.querySelector('#password')
var passwordConfirmation = plainRegisterForm.querySelector('#pass');
var fields = plainRegisterForm.querySelectorAll('.field');

generateError = function (text) {
    var error = document.createElement('div');
    error.className = 'validationError';
    error.innerHTML = text;
    return error;
}

var removeValidation = function () {
    var errors = plainRegisterForm.querySelectorAll('.validationError')
    console.log(errors);
    if(errors!=null){
        for (var i=0; i<errors.length;i++)
        errors[i].remove();
    }
}

var checkFieldsPresence = function () {
    for (var i = 0; i < fields.length; i++) {
        if (!fields[i].value) {
            console.log('field is blank', fields[i]);
            var error = generateError('Cannot be blank');
            fields[i].parentElement.insertBefore(error, fields[i]);
        }
    }
}

var checkPassword = function () {
    var lowerCaseLetters = /[a-z]/g;
    var error;
    if (!password.value.match(lowerCaseLetters)) {
        error = generateError('Must contain at least one lowercase letter');
        password.parentElement.insertBefore(error, password);
    }
    var upperCaseLetters = /[A-Z]/g;
    if (!password.value.match(upperCaseLetters)) {
        error = generateError('Must contain at least one uppercase letter');
        password.parentElement.insertBefore(error, password);
    }
    var numbers = /[0-9]/g;
    if (!password.value.match(numbers)) {
        error = generateError('Must contain at least one number');
        password.parentElement.insertBefore(error, password);
    }
    if (!password.value.length >= 8) {
        error = generateError('Must contain at least 8 or more characters');
        password.parentElement.insertBefore(error, password);
    }
}

var checkPasswordMatch = function () {
    if (password.value !== passwordConfirmation.value) {
        console.log('password do not equals');
        var error = generateError('Password does not match');
        password.parentElement.insertBefore(error, password);
    }
}

var checkNameFields = function () {
    var nameFields = plainRegisterForm.querySelector('#name');
    var regex = /^[A-Z]+[a-z]{1,10}$/g;
    if (!nameFields.value.match(regex)) {
        var error = generateError('Not a name');
        nameFields.parentElement.insertBefore(error, nameFields);
    }
}

var checkSurnameFields = function () {
    var nameFields = plainRegisterForm.querySelector('#surname');
    var regex = /^[A-Z]+[a-z]{1,20}$/g;
   if (!nameFields.value.match(regex)) {
        var error = generateError('Not a surname');
        nameFields.parentElement.insertBefore(error, nameFields);
    }
}

var checkEmail = function () {
    var email = plainRegisterForm.querySelector('#email');
    var regex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/g;
    if (!email.value.match(regex)) {
        var error = generateError('Invalid email');
        email.parentElement.insertBefore(error, email);

    }
}


plainRegisterForm.addEventListener('submit',  function (error) {
    error.preventDefault();

    removeValidation();

    checkFieldsPresence();

    checkPassword();

    checkPasswordMatch();

    checkNameFields();
	
	checkSurnameFields();

    checkEmail();
})

