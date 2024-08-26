// campland-N1 [hnlYXYbBDb]
(function() {
  $(function() {
    $(".campland-N1").each(function() {
      const $block = $(this);
      // Header Scroll
      $(window).on("load scroll", function() {
        const $thisTop = $(this).scrollTop();
        if ($thisTop > 120) {
          $block.addClass("header-top-active");
        } else {
          $block.removeClass("header-top-active");
        }
      });
      // Mobile Top
      $block.find(".btn-momenu").on("click", function() {
        $block.addClass("block-active");
      });
      $block.find(".btn-moclose").on("click", function() {
        $block.removeClass("block-active");
      });
      // Mobile Gnb
      $block.find(".header-gnbitem").each(function() {
        const $this = $(this);
        const $thislink = $this.find(".header-gnblink");
        $thislink.on("click", function() {
          if (!$(this).parent().hasClass("item-active")) {
            $(".header-gnbitem").removeClass("item-active");
          }
          $(this).parents(".header-gnbitem").toggleClass("item-active");
        });
      });
      // Header Mobile 1Depth Click
      if (window.innerWidth <= 992) {
        $block.find(".header-gnbitem").each(function() {
          const $gnblink = $(this).find(".header-gnblink");
          const $sublist = $(this).find(".header-sublist");
          if ($sublist.length) {
            $gnblink.attr("href", "javascript:void(0);");
          }
        });
      }
      // Full Gnb
      $block.find(".btn-allmenu").on("click", function() {
        $block.find(".header-fullmenu").addClass("fullmenu-active");
      });
      $block.find(".fullmenu-close").on("click", function() {
        $block.find(".header-fullmenu").removeClass("fullmenu-active");
      });
      // Full Gnb DecoLine
      $block.find(".fullmenu-gnbitem").each(function() {
        const $this = $(this);
        $this.on("mouseover", function() {
          if (window.innerWidth > 992) {
            $this.find(".fullmenu-gnblink").addClass("on");
          }
        });
        $this.on("mouseout", function() {
          if (window.innerWidth > 992) {
            $this.find(".fullmenu-gnblink").removeClass("on");
          }
        });
      });
    });




  });
})();

$(document).ready(function(){
    // URL에서 questionId 값 가져오기
    var urlCommParams = new URLSearchParams(window.location.search);
    var questionId = urlCommParams.get('questionId');
    console.log("question ID for Comment Controller :", questionId);

    // AJAX 요청을 통해 댓글 데이터 가져오기
    $.ajax({
        url: "boardQuestionComment", // 서버의 댓글 데이터 요청 URL
        type: "GET", // GET 또는 POST 요청 타입
        data: { questionId: questionId }, // 서버에 전송할 데이터
        success: function(data) {
            // 서버로부터 받은 데이터를 #questionComments 로드
            $("#questionComments").html(data);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.error("AJAX error :", textStatus, errorThrown);
        }
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
                     $('#userId').val(data.userId);
                    checkUserId(data.userId);
                    checkUserRole(data.userId, data.role);
                },
                error: function(error) {
                    console.error('Error:', error);
                }
            });
        } else {
            console.warn('No access token found in local storage.');
        }
    }

    // 작성자와 관리자 여부에 따라 댓글 입력 폼 표시
    function checkUserRole(userId, role) {
        const authorId = $('#authorId').text(); // 작성자의 userId를 가져옴
        console.log('User ID from token:', userId);
        console.log('Author ID from page:', authorId);
        if (userId === authorId || role === 'ADMIN') {
            $('.comment-form-section').show(); // 작성자 또는 관리자일 경우 댓글 작성 폼을 표시

        } else {
            $('.comment-form-section').hide(); // 그 외에는 댓글 작성 폼을 숨김

        }
    }

    // 작성자와 userId를 비교하여 수정하기 버튼 표시
          function checkUserId(userId) {
            const authorId = $('#authorId').text(); // 작성자의 userId를 가져옴
            console.log('User ID from token:', userId);
            console.log('Author ID from page:', authorId);
            if (userId === authorId) {
              $('#editButton').show();
              $('#deleteButton').show(); // userId가 일치하면 수정하기 버튼을 표시
            } else {
              $('#editButton').hide();
              $('#deleteButton').hide(); // userId가 일치하지 않으면 수정하기 버튼을 숨김
            }
          }



    // 문서가 준비되면 sendTokenToServer 함수를 호출
    sendTokenToServer();
});

