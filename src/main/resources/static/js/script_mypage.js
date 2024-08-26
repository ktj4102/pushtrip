let correctid = '';
let nameplan = '';
let userProfilePicture ='';

$(document).ready(function() {
    $(document).ready(function() {
    $("#zonecode").prop('readonly', true);
    $("#roadaddr").prop('readonly', true);
    $("#jibunaddr").prop('readonly', true);
            $('#search-address-btn').click(function() {
                new daum.Postcode({
                    oncomplete: function(data) {
                        // Address fields에 값을 설정
                        $('#zoneCode').val(data.zonecode);
                        $('#roadAddr').val(data.roadAddress);
                        $('#jibunAddr').val(data.jibunAddress);
                    }
                }).open();
            });
        });
            // 로그인 성공 후 페이지 로드 시 사용자 정보 요청
            const accessToken = localStorage.getItem('accessToken');
            if (accessToken) {
                $.ajax({
                    url: '/api/user-info',
                    method: 'GET',
                    headers: {
                        'Authorization': 'Bearer ' + accessToken
                    },
                    success: function(userInfo) {
                        $('#userId').text(userInfo.userId);
                        $('#userId1').text(userInfo.userId);
                        $('#password').text(userInfo.password);
                        $('#userEmail').text(userInfo.email);
                        $('#userEmail1').text(userInfo.email);
                        $('#userEmail2').text(userInfo.email);
                        $('#userName').text(userInfo.name);
                        $('#userName1').text(userInfo.name);
                        $('#userName2').text(userInfo.name);
                        $('#userName3').text(userInfo.name);
                        $('#userName4').text(userInfo.name);
                        $('#userName5').text(userInfo.name);
                        $('#userName6').text(userInfo.name);
                        $('#userName7').text(userInfo.name);
                        $('#userName8').text(userInfo.name);
                        $('#userName9').text(userInfo.name);
                        $('#userName10').text(userInfo.name);
                        $('#userName11').text(userInfo.name);
                        $('#userTel').text(userInfo.tel);
                        $('#userTel1').text(userInfo.tel);
                        $('#userTel2').text(userInfo.tel);
                        $('#userAccountNo').text(userInfo.accountNo);
                        $('#userGender').text(userInfo.gender);
                        $('#userBirth').text(userInfo.birth);
                        $('#userBirth1').text(userInfo.birth);
                        $('#userZoneCode').text(userInfo.zoneCode);
                        $('#userZoneCode1').text(userInfo.zoneCode);
                        $('#userRoadAddr').text(userInfo.roadAddr);
                        $('#userRoadAddr1').text(userInfo.roadAddr);
                        $('#userRoadDetail').text(userInfo.roadDetail);
                        $('#userRoadDetail1').text(userInfo.roadDetail);
                        $('#userJibunAddr').text(userInfo.jibunAddr);
                        $('#userJibunAddr1').text(userInfo.jibunAddr);
                        $('#userRole').text(userInfo.role);
                        $('#userInsertDate').text(userInfo.insertDate);
                        $('#userUpdateDate').text(userInfo.updateDate);

                        $('#email3').val(userInfo.email);
                        $('#userName21').val(userInfo.name);
                        $('#userTel3').val(userInfo.tel);
                        $('#userPicture2').val(userInfo.picture);


                        $('#userInput').val(userInfo.userId);
                        $('#userInput1').val(userInfo.userId);
                        $('#userInput2').val(userInfo.userId);
                        $('#userInput3').val(userInfo.userId);
                        $('#userInput4').val(userInfo.userId);
                        $('#userInput5').val(userInfo.userId);
                        $('#userInput6').val(userInfo.userId);
                        $('#userInput7').val(userInfo.userId);
                        $('#userInput8').val(userInfo.userId);
                        $('#userInput9').val(userInfo.userId);


                        correctid = userInfo.userId;
                        nameplan = userInfo.name;
                        userProfilePicture = userInfo.picture;

                        console.log('correctid:', correctid);

                        // Handle picture display
                        if (userInfo.picture) {
                            $('#userPicture').attr('src', userInfo.picture);
                            $('#userPicture1').attr('src', userInfo.picture);
                            $('#userPicture2').attr('src', userInfo.picture);

                        } else {
                            $('#userPicture').attr('src', 'default-profile.png'); // Fallback image
                            $('#userPicture1').attr('src', 'default-profile.png'); // Fallback image
                        }




                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        console.error('Error:', textStatus, errorThrown);
                        if (confirm('사용자 정보를 가져오는 데 실패했습니다. 로그인 화면으로 이동하시겠습니까?')) {
                            // 확인 버튼을 클릭하면 로그인 페이지로 이동
                            window.location.href = 'loginForm'; // 또는 실제 로그인 페이지 URL로 변경
                        }
                    }
                });
            } else {
                alert('로그인 정보가 없습니다.');
            }

        });

        function checkid() {
            const inputid = document.getElementById('idCheck').value.trim();
            const errorMessage = document.getElementById('id-error-message');

            if (inputid === correctid) {
                // 아이디가 일치하면 폼을 표시
                document.querySelector('.form-container-password').style.display = 'block';
                errorMessage.style.display = 'none'; // Hide error message
            } else {
                // 아이디가 일치하지 않을 경우 에러 메시지 표시
                errorMessage.textContent = '아이디가 일치하지 않습니다.';
                errorMessage.style.display = 'block';
            }
        }

        function checkPassword() {
            const password = document.getElementById('password').value;
            const passwordConfirm = document.getElementById('password-C').value;
            const errorMessage = document.getElementById('password-error-message');
            const successMessage = document.getElementById('password-success-message');

            // 비밀번호 패턴 정의
            const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+[\]{};':"\\|,.<>/?`~])[a-zA-Z\d!@#$%^&*()_+[\]{};':"\\|,.<>/?`~]{10,16}$/;

            // 비밀번호와 비밀번호 확인이 일치하는지 확인
            if (password !== passwordConfirm) {
                errorMessage.textContent = '비밀번호와 비밀번호 확인이 일치하지 않습니다.';
                errorMessage.style.display = 'block';
                successMessage.style.display = 'none'; // Hide success message
                return false;
            }

            // 비밀번호가 패턴에 맞는지 확인
            if (!passwordPattern.test(password)) {
                errorMessage.textContent = '비밀번호는 10~16자 길이의 영문 대소문자와 숫자, 특수문자를 포함해야 합니다.';
                errorMessage.style.display = 'block';
                successMessage.style.display = 'none'; // Hide success message
                return false;
            }

            // 비밀번호가 일치하고 패턴을 만족하는 경우
            errorMessage.textContent = '';
            errorMessage.style.display = 'none';
            successMessage.textContent = '비밀번호가 일치합니다.';
            successMessage.style.display = 'block'; // Show success message
            return true; // 폼 제출 허용
        }

        // 폼 제출 시 비밀번호 유효성 검사 함수 호출
        function validatePasswordForm() {
            return checkPassword(); // `checkPassword` 함수가 true를 반환해야 폼 제출이 진행됨
        }



