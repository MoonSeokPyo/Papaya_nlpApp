document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('contact-form');
    const successMessage = document.getElementById('success-message');

    form.addEventListener('submit', function (event) {
        event.preventDefault();

        // Simulate form submission
        setTimeout(() => {
            // Show success message
            successMessage.classList.remove('d-none');
            successMessage.textContent = 'Message sent successfully!';

            // Clear the form fields
            form.reset();

            // Hide the message after 3 seconds
            setTimeout(() => {
                successMessage.classList.add('d-none');
            }, 3000);
        }, 500);
    });
});