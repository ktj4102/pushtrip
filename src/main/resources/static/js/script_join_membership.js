$(document).ready(function() {

   $('#idCheck').click(function() {
       checkDuplicateId();
     });


        $('#emailCheck').click(function() {
            checkDuplicateEmail();
          });



   $("#zonecode").prop('readonly', true);
   $("#roadaddr").prop('readonly', true);
   $("#jibunaddr").prop('readonly', true);
   $("#zonecode_1").click(function() {
       console.log("zonecode >>> : ");
       new daum.Postcode({
         oncomplete: function(data) {
           $("#zonecode").val(data.zonecode);
           $("#roadaddr").val(data.roadAddress);
           $("#jibunaddr").val(data.jibunAddress);
         }
       }).open();
     });



    document.querySelectorAll('.email-button').forEach(button => {
        button.addEventListener('click', () => {
          const emailInput = document.querySelector('#email-input');
          emailInput.value = emailInput.value.split('@')[0] + '@' + button.dataset.value;
        });
      });

      document.querySelectorAll('.gender-button').forEach(button => {
        button.addEventListener('click', () => {
          const genderInput = document.getElementById('gender');
          genderInput.value = button.dataset.value;
        });
      });



    $('.gender-button').on('click', function() {
        $('.gender-button').removeClass('active');
        $(this).addClass('active');
    });

    $('.email-button').on('click', function() {
        $('.email-button').removeClass('active');
        $(this).addClass('active');
    });


    $('#submit-btn').on('click', function(event) {
         event.preventDefault(); // 기본 폼 제출 방지
         let isValid = true;

         // 모든 오류 메시지 숨김
         const errorMessages = document.querySelectorAll('.error-message');
         errorMessages.forEach(function(msg) {
         msg.style.display = 'none';
         msg.textContent = ''; // 이전 오류 메시지 초기화
         });

         // 각 입력값 가져오기
         const name = document.querySelector('input[name="name"]').value;
         const userId = document.querySelector('input[name="userId"]').value;
         const password = document.querySelector('input[name="password"]').value;
         const confirmPassword = document.getElementById("confirmPassword").value;
         const tel = document.querySelector('input[name="tel"]').value;
         const email = document.querySelector('input[name="email"]').value;
         const gender = document.querySelector('input[name="gender"]').value;
         const birth = document.querySelector('input[name="birth"]').value;
         const zoneCode = document.querySelector('input[name="zoneCode"]').value;
         const roadAddr = document.querySelector('input[name="roadAddr"]').value;
         const roadDetail = document.querySelector('input[name="roadDetail"]').value;
         const termsAccepted = document.getElementById("checkset-a-1-1").checked;
         const phoneCode = document.getElementById("phone-code").value;

         // 유효성 검사
         if (!name) {
             document.getElementById("name-error").textContent = '이름을 입력해주세요.';
             document.getElementById("name-error").style.display = 'block';
             isValid = false;
         }

         if (!userId) {
             document.getElementById("userId-error").textContent = '아이디를 입력해주세요.';
             document.getElementById("userId-error").style.display = 'block';
             isValid = false;
         }

         const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!#%*?&]{10,16}$/;

         if (!password) {
             document.getElementById("password-error").textContent = '비밀번호를 입력해주세요.';
             document.getElementById("password-error").style.display = 'block';
             isValid = false;
         } else if (!passwordPattern.test(password)) {
             document.getElementById("password-error").textContent = '비밀번호는  대문자, 소문자, 숫자 및 특수문자를 포함한10~16자이어야 합니다.';
             document.getElementById("password-error").style.display = 'block';
             isValid = false;
         }


         if (password !== confirmPassword) {
             document.getElementById("confirmPassword-error").textContent = '비밀번호가 일치하지 않습니다.';
             document.getElementById("confirmPassword-error").style.display = 'block';
             isValid = false;
         }


         const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
         if (!email) {
             document.getElementById("email-error").textContent = '이메일을 입력해주세요.';
             document.getElementById("email-error").style.display = 'block';
             isValid = false;
         } else if (!emailPattern.test(email)) {
             document.getElementById("email-error").textContent = '유효한 이메일 주소를 입력해주세요.';
             document.getElementById("email-error").style.display = 'block';
             isValid = false;
         }

         if (!gender) {
             document.getElementById("gender-error").textContent = '성별을 선택해주세요.';
             document.getElementById("gender-error").style.display = 'block';
             isValid = false;
         }

         const birthPattern = /^\d{4}-\d{2}-\d{2}$/;
         if (!birth) {
             document.getElementById("birth-error").textContent = '생년월일을 입력해주세요.';
             document.getElementById("birth-error").style.display = 'block';
             isValid = false;
         } else if (!birthPattern.test(birth)) {
             document.getElementById("birth-error").textContent = '생년월일은 YYYY-MM-DD 형식이어야 합니다.';
             document.getElementById("birth-error").style.display = 'block';
             isValid = false;
         }

         if (!zoneCode || !roadAddr ||!roadDetail) {
             document.getElementById("zoneCode-error").textContent = '주소 정보를 입력해주세요.';
             document.getElementById("zoneCode-error").style.display = 'block';
             isValid = false;
         }



         if (!termsAccepted) {
             document.getElementById("terms-error").textContent = '개인정보 수집 및 이용 동의에 체크해주세요.';
             document.getElementById("terms-error").style.display = 'block';
             isValid = false;
         }

         if (!phoneCode) {
             document.getElementById("phone-code-error").textContent = '전화번호 인증을 해주세요';
             document.getElementById("phone-code-error").style.display = 'block';
             isValid = false;
         }

         // 모든 입력이 유효한 경우 폼 제출


         if (isValid) {

             $('form').submit();
         }else{
             alert("회원가입 정보를 다시 확인해주세요.")

         }
    });

});





         function checkDuplicateId() {
             var userId = $('#userId').val();

               $('#userId-error').hide();
               $('#userId-success').hide();

             if (userId) {
                 $.ajax({
                     url: '/checkId', // 엔드포인트 URL
                     type: 'POST',
                     data: { userId: userId },
                     success: function(response) {
                         // 서버로부터의 응답 처리
                         if (response.success) {
                               document.getElementById("userId-error").textContent = '이미 사용중인 아이디입니다.';
                               document.getElementById("userId-error").style.display = 'block';
                               isValid = false;
                         } else {
                                document.getElementById("userId-success").textContent = '사용가능한 아이디입니다.';
                                document.getElementById("userId-success").style.display = 'block';
                         }
                     },
                     error: function(xhr, status, error) {
                         // 서버 오류나 네트워크 오류 처리
                         console.error('서버 오류:', status, error);
                         alert('서버 오류가 발생했습니다. 나중에 다시 시도해 주세요.');
                     }
                 });
             } else {
                 document.getElementById("userId-error").textContent = '아이디를 입력해주세요.';
                 document.getElementById("userId-error").style.display = 'block';
                 isValid = false;
             }

         }


            function checkDuplicateEmail() {
                     var emailPrefix = $('input[placeholder="이메일을 입력해주세요."]').val().trim();
                     var email = emailPrefix

                       $('#email-error').hide();
                       $('#email-success').hide();

                      if (email) {
                          $.ajax({
                              url: '/checkEmail', // 엔드포인트 URL
                              type: 'POST',
                              data: { email: email },
                              success: function(response) {
                                  // 서버로부터의 응답 처리
                                  if (response.success) {
                                       document.getElementById("email-error").textContent = '이미 사용중인 이메일입니다.';
                                       document.getElementById("email-error").style.display = 'block';
                                       isValid = false;
                                  } else {
                                       document.getElementById("email-success").textContent = '사용가능한 이메일입니다.';
                                       document.getElementById("email-success").style.display = 'block';
                                  }
                              },
                              error: function(xhr, status, error) {
                                  // 서버 오류나 네트워크 오류 처리
                                  console.error('서버 오류:', status, error);
                                  alert('서버 오류가 발생했습니다. 나중에 다시 시도해 주세요.');
                              }
                          });
                      } else {
                         document.getElementById("email-error").textContent = '이메일을 입력해주세요.';
                         document.getElementById("email-error").style.display = 'block';
                         isValid = false;
                      }

            }





               $('#send-phone-code').click(function() {

                 var tel = $('#tel').val();

                  $('#send-phone-code-error').hide();
                  $('#send-phone-code-success').hide();

                   if (tel) {
                               $.ajax({
                                   url: '/api/insert-send-message',
                                   type: 'POST',
                                   data: { tel: tel }, // 핸드폰 번호를 쿼리 파라미터로 전송
                                   success: function(response) {
                                       if (response.success) {
                                           document.getElementById("send-phone-code-success").textContent = '인증번호가 발송되었습니다';
                                           document.getElementById("send-phone-code-success").style.display = 'block';
                                       } else {
                                           document.getElementById("send-phone-code-error").textContent = '이미 가입된 전화번호입니다.';
                                           document.getElementById("send-phone-code-error").style.display = 'block';
                                           isValid = false;
                                       }
                                   },
                                   error: function(xhr, status, error) {
                                       console.error('서버 오류:', status, error);
                                       console.error( error);
                                       alert('형식에 맞춰 입력해주세요');
                                   }
                               });
                   } else {
                         document.getElementById("send-phone-code-error").textContent = '핸드폰 번호를 입력해주세요.';
                         document.getElementById("send-phone-code-error").style.display = 'block';
                         isValid = false;
                   }


             });



             $('#verify-phone-code').on('click', function() {
                    let code = $('#phone-code').val();
                    let tel = $('#tel').val();


                    $('#phone-code-error').hide();
                    $('#phone-code-success').hide();

                    if (!code) {
                        document.getElementById("phone-code-error").textContent = '인증번호를 입력해주세요.';
                        document.getElementById("phone-code-error").style.display = 'block';
                        isValid = false;
                        return;
                    }
                    $.ajax({
                        url: '/api/insert-verify-code',
                        type: 'POST',
                        data: { code: code, tel: tel },
                        success: function(response) {
                            if (response.success) {
                                document.getElementById("phone-code-success").textContent = '인증번호가 확인되었습니다';
                                document.getElementById("phone-code-success").style.display = 'block';


                            } else {
                                document.getElementById("phone-code-error").textContent = '인증번호 확인에 실패하였습니다.';
                                document.getElementById("phone-code-error").style.display = 'block';
                                isValid = false;
                            }
                        },
                        error: function() {
                            alert('서버 오류');
                        }
                    });
             });






