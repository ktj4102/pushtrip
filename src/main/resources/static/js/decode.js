 function sendTokenToServer() {
        const token = localStorage.getItem('accessToken');

        if (token) {
            $.ajax({
                url: '/decode-token',
                type: 'POST',
                contentType: 'application/json',
                headers: {
                    'Authorization': `Bearer ${token}`
                },
                success: function(data) {
                   $('#userId').val(data.userId);
                   $('input[name="userId"]').val(data.userId);

                },
                error: function(error) {
                    console.error('Error:', error);
                }
            });
        } else {
            console.warn('No access token found in local storage.');
        }
    }

    $(document).ready(function() {
        sendTokenToServer();
    });