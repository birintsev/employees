$(document).ready(function () {
    $('#newPasswordForm').validate({
        rules: {
            resetPasswordToken: {
                required: true
            },
            password: {
                required: true, /*todo create check constraint on password column (length 8...50)*/
                minLength: 8,
                maxLength: 50
            },
            newPasswordConfirmation: {
                required: true,
                equalTo: '#password'
            }
        }
    });
});