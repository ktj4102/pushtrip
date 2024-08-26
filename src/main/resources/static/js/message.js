    $(document).ready(function () {
    const accessToken = localStorage.getItem('accessToken');

    // 각 버튼 클릭 시 섹션 전환 및 삭제 버튼 표시/숨기기
    $('#receivedMessagesButton').on('click', function () {
        showSection('receivedMessagesSection');
        loadReceivedMessages();
    });

    $('#sentMessagesButton').on('click', function () {
        showSection('sentMessagesSection');
        loadSentMessages();
    });

    $('#writeMessageButton').on('click', function () {
        showSection('writeMessageSection');
    });

    $('#messageForm').on('submit', function (e) {
            e.preventDefault(); // 폼 제출 기본 동작 막기

            const receiverUserId = $('#receiverUserId').val();
            const title = $('#title').val();
            const content = $('#content').val();

            // 쪽지 보내기 요청
            $.ajax({
                url: '/messages',
                method: 'POST',
                contentType: 'application/json',
                headers: {
                    'Authorization': 'Bearer ' + accessToken
                },
                data: JSON.stringify({
                    receiverUserId: receiverUserId,
                    title: title,
                    content: content
                }),
                success: function () {
                    alert('쪽지가 성공적으로 보내졌습니다.');
                    $('#messageForm')[0].reset();  // 폼 초기화
                    showSection('sentMessagesSection');  // 보낸 쪽지함으로 이동
                    loadSentMessages();  // 보낸 쪽지함 목록 갱신
                },
                error: function (xhr, status, error) {
                    alert('쪽지 보내기에 실패했습니다.');
                    console.error("Error:", error);
                }
            });
        });

    // 섹션을 표시하고 다른 섹션을 숨기기
    function showSection(sectionId) {
        // 모든 섹션을 숨기고
        $('#receivedMessagesSection, #sentMessagesSection, #writeMessageSection').addClass('hidden');
        // 해당 섹션만 보이기
        $('#' + sectionId).removeClass('hidden');
    }

    // 받은 쪽지함 데이터 로드
    function loadReceivedMessages() {
        $.ajax({
            url: '/messages/received',
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + accessToken
            },
            success: function (response) {
                updateMessageTable(response.data, 'receivedMessagesTableBody', 'senderUserId');
            },
            error: function (xhr, status, error) {
                console.error("Error:", error);
            }
        });
    }

    // 보낸 쪽지함 데이터 로드
    function loadSentMessages() {
        $.ajax({
            url: '/messages/sent',
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + accessToken
            },
            success: function (response) {
                updateMessageTable(response.data, 'sentMessagesTableBody', 'receiverUserId');
            },
            error: function (xhr, status, error) {
                console.error("Error:", error);
            }
        });
    }

    // 메시지 테이블 갱신 함수
    function updateMessageTable(messages, tableBodyId, userIdField) {
        const tbody = $('#' + tableBodyId);
        tbody.empty();
        messages.forEach(function (message) {
            tbody.append(`
                <tr>
                    <td><input type="checkbox" class="messageCheckbox" data-message-id="${message.id}"></td>
                    <td>${message[userIdField]}</td>
                    <td>${message.title}</td>
                    <td>${message.content}</td>
                    <td>${message.insertDate}</td>
                </tr>
            `);
        });

        // 전체 선택/해제 기능
        $('#selectAllReceived, #selectAllSent').on('change', function () {
            const isChecked = $(this).is(':checked');
            $(this).closest('table').find('.messageCheckbox').prop('checked', isChecked);
        });
    }

    // 받은 쪽지함 선택 삭제
    $('#deleteReceivedMessagesButton').on('click', function () {
        const selectedMessageIds = [];
        $('#receivedMessagesTableBody .messageCheckbox:checked').each(function () {
            selectedMessageIds.push($(this).data('message-id'));
        });

        if (selectedMessageIds.length > 0) {
            if (confirm('선택한 쪽지를 삭제하시겠습니까?')) {
                const ajaxRequests = [];  // 모든 Ajax 요청을 저장할 배열

                selectedMessageIds.forEach(function (id) {
                    // 각 삭제 요청을 배열에 저장
                    const ajaxRequest = $.ajax({
                        url: `/messages/received/${id}`,
                        method: 'DELETE',
                        headers: {
                            'Authorization': 'Bearer ' + accessToken
                        },
                        success: function () {
                            console.log(`메시지 ${id} 삭제 성공`);
                        },
                        error: function (xhr, status, error) {
                            alert(`메시지 ${id} 삭제에 실패했습니다.`);
                            console.error("Error:", error);
                        }
                    });

                    ajaxRequests.push(ajaxRequest);  // 배열에 Ajax 요청 추가
                });

                // 모든 Ajax 요청이 완료된 후 호출
                $.when.apply($, ajaxRequests).then(function () {
                    // 삭제 완료 후 받은 쪽지 목록 갱신
                    loadReceivedMessages();
                    alert("삭제 되었습니다.");
                });
            }
        } else {
            alert('삭제할 쪽지를 선택하세요.');
        }
    });

    // 보낸 쪽지함 선택 삭제
    $('#deleteSentMessagesButton').on('click', function () {
        const selectedMessageIds = [];
        $('#sentMessagesTableBody .messageCheckbox:checked').each(function () {
            selectedMessageIds.push($(this).data('message-id'));
        });

        if (selectedMessageIds.length > 0) {
            if (confirm('선택한 쪽지를 삭제하시겠습니까?')) {
                const ajaxRequests = [];  // 모든 Ajax 요청을 저장할 배열

                selectedMessageIds.forEach(function (id) {
                    // 각 삭제 요청을 배열에 저장
                    const ajaxRequest = $.ajax({
                        url: `/messages/sent/${id}`,
                        method: 'DELETE',
                        headers: {
                            'Authorization': 'Bearer ' + accessToken
                        },
                        success: function () {
                            console.log(`메시지 ${id} 삭제 성공`);
                        },
                        error: function (xhr, status, error) {
                            alert(`메시지 ${id} 삭제에 실패했습니다.`);
                            console.error("Error:", error);
                        }
                    });

                    ajaxRequests.push(ajaxRequest);  // 배열에 Ajax 요청 추가
                });

                // 모든 Ajax 요청이 완료된 후 호출
                $.when.apply($, ajaxRequests).then(function () {
                    // 삭제 완료 후 보낸 쪽지 목록 갱신
                    loadSentMessages();
                    alert("삭제 되었습니다.");
                });
            }
        } else {
            alert('삭제할 쪽지를 선택하세요.');
        }
    });
});