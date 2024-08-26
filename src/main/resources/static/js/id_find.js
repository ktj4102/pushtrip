
  $(document).ready(function() {

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




      $('#verify-phone-code').click(function() {

       var phoneCode = $('#phone-code').val();
       var tel = $('#phone-number').val();
        if (tel && phoneCode) {
            $.ajax({
                url: '/api/verify-phone-code',
                type: 'POST',
                data: {
                 phoneCode : phoneCode,
                 tel : tel
                 }, // 핸드폰 번호를 쿼리 파라미터로 전송
                success: function(response) {
                    if (response.success) {
                        document.getElementById("phone-code-success").textContent = '인증이 완료되었습니다.';
                        document.getElementById("phone-code-success").style.display = 'block';
                         $('#phone-verification-section').hide();
                         $('#id-result-section').show();
                         $('#user-id').text(response.userId);
                    } else {
                        document.getElementById("phone-code-error").textContent = '인증이 확인에 실패하였습니다.';
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


      $('#back-to-form').click(function() {
        // 초기화 작업


        $('#phone-number-error').hide();
        $('#phone-number-success').hide();
        $('#phone-code-error').hide();
        $('#phone-code-success').hide();
        $('#phone-number').val('');
        $('#phone-code').val('');
        $('#phone-verification-section').hide();
        $('#id-result-section').hide();
        // 입력 폼 재 표시
        $('#find-id-form').show();
      });
  });