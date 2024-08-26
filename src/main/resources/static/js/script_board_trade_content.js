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
    console.log("js working!");
    // URL에서 tradeId 값 가져오기
    var urlCommParams = new URLSearchParams(window.location.search);
    var tradeId = urlCommParams.get('tradeId');
    console.log("Trade ID for Comment Controller :", tradeId);

    // AJAX 요청을 통해 댓글 데이터 가져오기
    $.ajax({
        url: "boardTradeComment", // 서버의 댓글 데이터 요청 URL
        type: "GET", // GET 또는 POST 요청 타입
        data: { tradeId: tradeId }, // 서버에 전송할 데이터
        success: function(data) {
            // 서버로부터 받은 데이터를 #tradeComments에 로드
            $("#tradeComments").html(data);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.error("AJAX error :", textStatus, errorThrown);
        }
    });
});