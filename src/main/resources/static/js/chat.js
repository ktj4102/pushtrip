   $(document).ready(function() {
      sendTokenToServer();
    });


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
                 console.log('Role data retrieved successfully:', data);

                 const userId = data.userId;

                 // userId를 이용해 다른 AJAX 호출 수행
                 $.ajax({
                     url: "chattingRoom",
                     type: "GET",
                     data: { userId: userId }, // 서버에 전송할 데이터
                     success: function(data) {
                         $("#chattingRoom").html(data);
                     },
                     error: function(jqXHR, textStatus, errorThrown) {
                         console.error("AJAX error :", textStatus, errorThrown);
                     }
                 });
             },
             error: function(error) {
                 console.error('Error:', error);
             }
         });
     } else {
         console.warn('No access token found in local storage.');
     }
 }