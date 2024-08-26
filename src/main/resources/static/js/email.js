$(document).ready(function() {
    $('#find-password').click(function() {
        var userid = $('#userid').val();
        if (userid) {
            $('#auth-options').show();
        } else {
            alert('아이디를 입력해주세요.');
        }
    });

    $('#phone-auth').click(function() {
        $('#auth-options').hide();
        $('#phone-auth-section').show();
    });

    $('#email-auth').click(function() {
        $('#auth-options').hide();
        $('#email-auth-section').show();
    });

    $('#send-phone-code').click(function() {
        var phoneNumber = $('#phone-number').val();
        if (phoneNumber) {
            $('#phone-verification-section').show();
        } else {
            alert('전화번호를 입력해주세요.');
        }
    });

    $('#send-email-code').click(function() {
        var emailAddress = $('#email-address').val();
        if (emailAddress) {
            $.post('/api/send-email-code', { email: emailAddress })
                .done(function(response) {
                    if (response.success) {
                        $('#email-verification-section').show();
                        alert('인증 코드가 이메일로 발송되었습니다.');
                    } else {
                        alert('인증 코드 발송에 실패했습니다.');
                    }
                });
        } else {
            alert('이메일 주소를 입력해주세요.');
        }
    });

    $('#verify-phone-code').click(function() {
        var phoneCode = $('#phone-code').val();
        if (phoneCode) {
            alert('전화번호 인증이 완료되었습니다.');
            // 여기에 실제 인증 확인 로직 추가
            window.location.href = 'login.html'; // 인증 성공 시 로그인 페이지로 리디렉션
        } else {
            alert('인증 코드를 입력해주세요.');
        }
    });

  $('#verify-email-code').click(function() {
      var emailAddress = $('#email-address').val();
      var emailCode = $('#email-code').val();

      if (emailAddress && emailCode) {
          $.ajax({
              url: '/verify-email-code', // 엔드포인트 URL
              type: 'POST',
              data: {
                  email: emailAddress,
                  code: emailCode
              },
              success: function(response) {
                  if (response.success) {
                      alert('이메일 인증이 완료되었습니다. 임시 비밀번호가 발송되었습니다.');
                      // 인증 성공 후 로그인 페이지로 이동
                      window.location.href = '/login';
                  } else {
                      alert('인증 코드가 올바르지 않습니다.');
                  }
              },
              error: function(xhr, status, error) {
                  // 서버 오류나 네트워크 오류 처리
                  console.error('서버 오류:', status, error);
                  alert('서버 오류가 발생했습니다. 나중에 다시 시도해 주세요.');
              }
          });
      } else {
          alert('이메일 주소와 인증 코드를 입력해주세요.');
      }
  });

    $('#retry-phone-auth').click(function() {
        resetToInitial();
    });

    $('#retry-email-auth').click(function() {
        resetToInitial();
    });

    $('#reset-id').click(function() {
        resetToInitial();
    });

    function resetToInitial() {
        // 입력 필드 초기화
        $('#userid').val('');
        $('#phone-number').val('');
        $('#phone-code').val('');
        $('#email-address').val('');
        $('#email-code').val('');

        // 화면 상태 초기화
        $('#auth-options').hide();
        $('#phone-auth-section').hide();
        $('#phone-verification-section').hide();
        $('#email-auth-section').hide();
        $('#email-verification-section').hide();
    }
});



