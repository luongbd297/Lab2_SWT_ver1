/* ------------------------------------ Click on login and Sign Up to  changue and view the effect
 ---------------------------------------
 */

const time_to_show_login = 400;
const time_to_hidden_login = 200;

function  change_to_login() {
    document.querySelector(".cont_forms").className =
            "cont_forms cont_forms_active_login";
    document.querySelector(".cont_form_login").style.display = "block";
    document.querySelector(".cont_form_sign_up").style.opacity = "0";

    setTimeout(function () {
        document.querySelector(".cont_form_login").style.opacity = "1";
    }, time_to_show_login);

    setTimeout(function () {
        document.querySelector(".cont_form_sign_up").style.display = "none";
    }, time_to_hidden_login);
}

const time_to_show_sign_up = 100;
const time_to_hidden_sign_up = 400;

function change_to_sign_up(at) {
    document.querySelector(".cont_forms").className =
            "cont_forms cont_forms_active_sign_up";
    document.querySelector(".cont_form_sign_up").style.display = "block";
    document.querySelector(".cont_form_login").style.opacity = "0";

    setTimeout(function () {
        document.querySelector(".cont_form_sign_up").style.opacity = "1";
    }, time_to_show_sign_up);

    setTimeout(function () {
        document.querySelector(".cont_form_login").style.display = "none";
    }, time_to_hidden_sign_up);
}

const time_to_hidden_all = 500;

function hidden_login_and_sign_up() {
    document.querySelector(".cont_forms").className = "cont_forms";
    document.querySelector(".cont_form_sign_up").style.opacity = "0";
    document.querySelector(".cont_form_login").style.opacity = "0";

    setTimeout(function () {
        document.querySelector(".cont_form_sign_up").style.display = "none";
        document.querySelector(".cont_form_login").style.display = "none";
    }, time_to_hidden_all);
}

function submitForm(formName) {
    document.forms[formName].submit();
}

function validateLogin() {
    var accountInput = document.getElementById("account");
    var passwordInput = document.getElementById("password");
    var accountError = document.getElementById("accountError");
    var passwordError = document.getElementById("passwordError");

    if (accountInput.value.trim() === "") {
        accountError.textContent = "Please enter your account.";
        return false;
    }
    accountError.textContent = "";

    if (passwordInput.value.trim() === "") {
        passwordError.textContent = "Please enter your password.";
        return false;
    }
    passwordError.textContent = "";

    return true;
}

function validateSignup() {
    var nameInput = document.getElementById("name");
    var accountInput = document.getElementById("account");
    var passwordInput = document.getElementById("password");
    var confirmPasswordInput = document.getElementById("confirmPassword");
    var nameError = document.getElementById("nameError");
    var accountError = document.getElementById("accountError");
    var passwordError = document.getElementById("passwordError");
    var confirmPasswordError = document.getElementById("confirmPasswordError");

    if (nameInput.value.trim() === "") {
        nameError.textContent = "Please enter your name.";
        return false;
    }
    nameError.textContent = "";

    if (accountInput.value.trim() === "") {
        accountError.textContent = "Please enter your account.";
        return false;
    }
    accountError.textContent = "";

    if (passwordInput.value.trim() === "") {
        passwordError.textContent = "Please enter your password.";
        return false;
    }
    passwordError.textContent = "";

    if (confirmPasswordInput.value.trim() === "") {
        confirmPasswordError.textContent = "Please confirm your password.";
        return false;
    }
    confirmPasswordError.textContent = "";

    if (passwordInput.value !== confirmPasswordInput.value) {
        confirmPasswordError.textContent = "Passwords do not match.";
        return false;
    }
    confirmPasswordError.textContent = "";

    return true;
}