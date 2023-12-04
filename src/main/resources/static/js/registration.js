function checkRegistrationForm(){
    return (checkLoginForm() && (loginIsCorrect() && passwordIsCorrect() && passwordsIsNotEqual()));
}