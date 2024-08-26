$(document).ready(function(){
    console.log("boardFreeComment.js working!");

    // URL에서 freeId 값 가져오기
    var urlParams = new URLSearchParams(window.location.search);
    var freeId = urlParams.get('freeId');
    console.log("Free ID for CommentInsert :", freeId);

    // freeId 값을 input 필드에 설정
    $('#freeId').val(freeId);


    // 더 보기 버튼 클릭 시 메뉴 토글
    $('.more-options-btn').on('click', function(event) {
        event.stopPropagation();
        var $menu = $(this).next('.more-options-menu');

        // 모든 메뉴 닫기
        $('.more-options-menu').not($menu).hide();

        // 메뉴 토글
        $menu.toggle();

        // 메뉴 외부 클릭 시 메뉴 닫기
        $(document).on('click', function(e) {
            if (!$(e.target).closest('.more-options').length) {
                $('.more-options-menu').hide();
            }
        });
    });

    // 수정 버튼 클릭 시 AJAX 요청
    $(document).on('click', '.edit-btn', function() {
        var commentId = $(this).data('id');

        // 여기에 수정 요청을 위한 AJAX 호출 추가
        $.ajax({
            url: '/your-endpoint/updateComment', // 실제 수정 요청 URL로 교체
            method: 'POST',
            data: { id: commentId },
            success: function(response) {
                // 성공적인 응답 처리

            },
            error: function(xhr, status, error) {
                // 에러 처리

            }
        });
    });

    // 삭제 버튼 클릭 시 AJAX 요청
    $(document).on('click', '.delete-btn', function() {
        var freeCommId = $(this).data('id');
        console.log("freeCommId for Comment Controller :", freeCommId);
        if (confirm('정말로 이 댓글을 삭제하시겠습니까?')) {
            $.ajax({
                url: 'freeForumCommentDelete',
                method: 'POST',
                data: { freeCommId: freeCommId },
                success: function(response) {
                    // 성공적인 응답 처리
                    location.reload(); // 페이지 새로고침하여 댓글 목록 업데이트
                },
                error: function(xhr, status, error) {
                    // 에러 처리
                }
            });
        }
    });
});