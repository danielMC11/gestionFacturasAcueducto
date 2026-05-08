function togglePasswordVisibility(inputId, iconId) {
    const passwordInput = document.getElementById(inputId);
    const icon = document.getElementById(iconId);
    if (passwordInput.type === "password") {
        passwordInput.type = "text";
        icon.setAttribute("src", "/img/showpassword.png");
    } else {
        passwordInput.type = "password";
        icon.setAttribute("src", "/img/hidepassword.png");
    }
}
