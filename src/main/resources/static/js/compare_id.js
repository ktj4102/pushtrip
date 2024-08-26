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
          console.log('Token decoded successfully:', data);
          $('#userId').val(data.userId);
          checkUserId(data.userId); // userId를 받은 후 비교 함수 호출
        },
        error: function(error) {
          console.error('Error:', error);
        }
      });
    } else {
      console.warn('No access token found in local storage.');
    }
  }

  // 작성자와 userId를 비교하여 수정하기 버튼 표시
  function checkUserId(userId) {
    const authorId = $('#authorId').text(); // 작성자의 userId를 가져옴
    console.log('User ID from token:', userId);
    console.log('Author ID from page:', authorId);
    if (userId === authorId) {
      $('#editButton').show(); // userId가 일치하면 수정하기 버튼을 표시
      $('#deleteButton').show();
    } else {
      $('#editButton').hide(); // userId가 일치하지 않으면 수정하기 버튼을 숨김
      $('#deleteButton').hide();
    }
  }



  // 문서가 준비되면 sendTokenToServer 함수를 호출
  $(document).ready(function() {
    sendTokenToServer();
  });