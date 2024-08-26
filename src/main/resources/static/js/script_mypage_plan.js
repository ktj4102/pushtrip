let correctid = '';

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


                        correctid = userInfo.userId;
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

                        document.getElementById('viewScheduleBtn').addEventListener('click', function() {
                            var userId = userInfo.userId; // userId를 폼 필드에서 가져옵니다
                            if (userId) {
                                $.ajax({
                                    url: 'travelPlannerSelectAll',
                                    type: 'GET',
                                    data: { userId: userId },
                                    success: function(data) {
                                        updatePlannerList(data);
                                    },
                                    error: function(xhr, status, error) {
                                        alert('일정 정보를 가져오는 중 오류가 발생했습니다.');
                                    }
                                });
                            } else {
                                alert('User ID가 필요합니다.');
                            }
                        });

                        function updatePlannerList(pList) {
                            var plannerList = document.querySelector('.planner-list');
                            plannerList.innerHTML = ''; // 기존 내용을 비웁니다.

                            pList.forEach(function(travelPlanner) {
                                var listItem = `
                                    <li class="planner-item">
                                        <div class="planner-header">
                                            <span class="planner-planType">${travelPlanner.planType}</span>
                                            <span class="planner-planDate">${travelPlanner.planDate}</span>
                                            <span class="planner-planTime">${travelPlanner.planTime}</span>
                                            <span class="planner-roadAddr">${travelPlanner.roadAddr}</span>
                                            <span class="planner-jibunAddr">${travelPlanner.jibunAddr}</span>
                                            <span class="planner-planCost">${travelPlanner.planCost}</span>
                                            <span class="planner-insertdate">${new Date(travelPlanner.insertDate).toLocaleString()}</span>

                                            <p class="planner-planMemo"><span>메모 : </span>${travelPlanner.planMemo}</p>

                                            <div class="more-options">
                                                <button class="more-options-btn">⋮</button>
                                                <div class="more-options-menu">
                                                    <button class="edit-btn" data-id="${travelPlanner.planId}">수정</button>
                                                    <button class="delete-btn" data-id="${travelPlanner.planId}">삭제</button>
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                `;
                                plannerList.innerHTML += listItem;
                            });
                        }


                        // 삭제 버튼 클릭 시 AJAX 요청
                        $(document).on('click', '.delete-btn', function() {
                            var planId = $(this).data('id');

                            if (confirm('정말로 이 일정을 삭제하시겠습니까?')) {
                                $.ajax({
                                    url: 'travelPlannerDelete',
                                    method: 'POST',
                                    data: { planId: planId },
                                    success: function(response) {
                                        // 성공적인 응답 처리
                                        location.reload(); // 페이지 새로고침하여 댓글 목록 업데이트
                                    },
                                    error: function(xhr, status, error) {
                                        // 에러 처
                                    }
                                });
                            }
                        });


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


