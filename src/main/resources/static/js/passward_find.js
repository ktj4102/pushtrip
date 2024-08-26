$(document).ready(function() {

    $('#find-password').click(function() {
        var userId = $('#userid').val();
        if (userid) {

           $.ajax({
                     url: '/checkId', // 엔드포인트 URL
                     type: 'POST',
                     data: { userId: userId },
                     success: function(response) {
                         // 서버로부터의 응답 처리
                         if (response.success) {
                             $('#auth-options').show();

                             document.getElementById("userid-success").textContent = '아이디가 확인되었습니다.';
                             document.getElementById("userid-success").style.display = 'block';
                         } else {
                             document.getElementById("userid-error").textContent = '존재하지않는 아이디입니다.';
                             document.getElementById("userid-error").style.display = 'block';
                         }
                     },
                     error: function(xhr, status, error) {
                         // 서버 오류나 네트워크 오류 처리
                         console.error('서버 오류:', status, error);
                         alert('서버 오류가 발생했습니다. 나중에 다시 시도해 주세요.');
                     }
                 });

        } else {
            document.getElementById("userid-error").textContent = '아이디를 입력해주세요.';
            document.getElementById("userid-error").style.display = 'block';
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
           event.preventDefault(); // 기본 폼 제출 동작을 막음

            var tel = $('#phone-number').val();


            if (tel) {
                $.ajax({
                    url: '/api/send-message',
                    type: 'POST',
                    data: { tel: tel }, // 핸드폰 번호를 쿼리 파라미터로 전송
                    success: function(response) {
                       if (response.success) {
                           document.getElementById("phone-number-success").textContent = '인증번호가 발송되었습니다.';
                           document.getElementById("phone-number-success").style.display = 'block';
                           $('#phone-verification-section').show();
                       } else {
                           document.getElementById("phone-number-error").textContent = '핸드폰 번호를 확인해주세요.';
                           document.getElementById("phone-number-error").style.display = 'block';
                        }
                    },
                    error: function(xhr, status, error) {
                        console.error('서버 오류:', status, error);
                        alert('서버 오류가 발생했습니다. 나중에 다시 시도해 주세요.');
                    }
                });
            } else {
                 document.getElementById("phone-number-error").textContent = '핸드폰 번호를 입력해주세요.';
                 document.getElementById("phone-number-error").style.display = 'block';
            }

     });

    $('#send-email-code').click(function() {
    var emailAddress = $('#email-address').val();
    var userId = $('#userid').val();
    if (emailAddress && userId) {
        $.post('/send-email-code', {
            email: emailAddress,  // Added comma here
            userId: userId        // Added comma here
        })
        .done(function(response) {
            if (response.success) {
                $('#email-verification-section').show();
                 document.getElementById("email-address-success").textContent = '인증 코드가 이메일로 발송되었습니다.';
                 document.getElementById("email-address-success").style.display = 'block';
            } else {
                document.getElementById("email-address-error").textContent = '이메일을 확인해주세요.';
                document.getElementById("email-address-error").style.display = 'block';
            }
        })
        .fail(function(xhr, status, error) {
            console.error('서버 오류:', status, error);
            alert('서버 오류가 발생했습니다. 나중에 다시 시도해 주세요.');
        });
    } else {
        document.getElementById("email-address-error").textContent = '이메일을 입력해주세요.';
        document.getElementById("email-address-error").style.display = 'block';
    }
});


    $('#verify-phone-code').click(function() {
       var phoneCode = $('#phone-code').val();
       var tel = $('#phone-number').val();
         if (tel && phoneCode) {
             $.ajax({
                 url: '/api/verify-phone-code-password',
                 type: 'POST',
                 data: {
                 phoneCode : phoneCode,
                 tel : tel
                 }, // 핸드폰 번호를 쿼리 파라미터로 전송
                 success: function(response) {
                     if (response.success) {
                         document.getElementById("phone-code-success").textContent = '핸드폰으로 임시비밀번호가 발급되었습니다.';
                         document.getElementById("phone-code-success").style.display = 'block';
                         $('#phone-auth-section').hide();
                     } else {
                         document.getElementById("phone-code-error").textContent = '인증에 실패하였습니다.';
                         document.getElementById("phone-code-error").style.display = 'block';
                     }
                 },
                 error: function(xhr, status, error) {
                     console.error('서버 오류:', status, error);
                     alert('서버 오류가 발생했습니다. 나중에 다시 시도해 주세요.');
                 }
             });
         } else {
              document.getElementById("phone-code-error").textContent = '인증코드를 입력해주세요.';
              document.getElementById("phone-code-error").style.display = 'block';
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
                      document.getElementById("email-code-success").textContent = '이메일 인증이 완료되었습니다. 임시 비밀번호가 발송되었습니다.';
                      document.getElementById("email-code-success").style.display = 'block';
                      alert('이메일로 임시비밀번호가 발송되었습니다.')
                      window.location.href = '/loginForm';
                  } else {
                      document.getElementById("email-code-error").textContent = '인증코드가 올바르지 않습니다.';
                      document.getElementById("email-code-error").style.display = 'block';
                  }
              },
              error: function(xhr, status, error) {
                  // 서버 오류나 네트워크 오류 처리
                  console.error('서버 오류:', status, error);
                  alert('서버 오류가 발생했습니다. 나중에 다시 시도해 주세요.');
              }
          });
      } else {
          document.getElementById("email-code-error").textContent = '인증코드를 입력해주세요.';
          document.getElementById("email-code-error").style.display = 'block';
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