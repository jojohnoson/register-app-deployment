document.addEventListener('DOMContentLoaded', function() {
    console.log('Registration form loaded');
    
    // Form validation
    const form = document.querySelector('form');
    const password = document.getElementById('psw');
    const confirmPassword = document.getElementById('psw-repeat');
    
    form.addEventListener('submit', function(e) {
        if (password.value !== confirmPassword.value) {
            alert('Passwords do not match!');
            e.preventDefault();
        }
    });
});