// campland-N1 [jAlyQxnF5f]
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
// campland-N3 [czlYQxnF90]
(function() {
  $(function() {
    $(".campland-N3").each(function() {
      const $block = $(this);
      // Swiper
      const swiper = new Swiper(".campland-N3 .contents-swiper", {
        slidesPerView: 1,
        spaceBetween: 0,
        loop: true,
        autoplay: {
          delay: 5000,
        },
        pagination: {
          el: ".campland-N3 .swiper-pagination",
          clickable: true,
        },
        navigation: {
          nextEl: ".campland-N3 .swiper-button-next",
          prevEl: ".campland-N3 .swiper-button-prev",
        },
      });
      // Swiper Play, Pause Button
      const pauseButton = $block.find('.swiper-button-pause');
      const playButton = $block.find('.swiper-button-play');
      playButton.hide();
      pauseButton.show();
      pauseButton.on('click', function() {
        swiper.autoplay.stop();
        playButton.show();
        pauseButton.hide();
      });
      playButton.on('click', function() {
        swiper.autoplay.start();
        playButton.hide();
        pauseButton.show();
      });
    });
  });
})();
// campland-N6 [BglYqXnfMC]
(function() {
  $(function() {
    $(".campland-N6").each(function() {
      const $block = $(this);
      // Swiper
      const swiper = new Swiper(".campland-N6 .contents-swiper", {
        slidesPerView: 1,
        spaceBetween: 0,
        loop: true,
        autoplay: {
          delay: 5000,
        },
        navigation: {
          nextEl: ".campland-N6 .swiper-button-next",
          prevEl: ".campland-N6 .swiper-button-prev",
        },
      });
    });
  });
})();

    $(document).ready(function() {
      sendTokenToServer();
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

                 const userId = data.userId;

                 // userId를 이용해 다른 AJAX 호출 수행
                 $.ajax({
                     url: "chattingRoom",
                     type: "GET",
                     data: { userId: userId }, // 서버에 전송할 데이터
                     success: function(data) {
                         $("#chattingRoom").html(data);
                     },
                     error: function(jqXHR, textStatus, errorThrown) {
                         console.error("AJAX error :", textStatus, errorThrown);
                     }
                 });
             },
             error: function(error) {
                 console.error('Error:', error);
             }
         });
     } else {
         console.warn('No access token found in local storage.');
     }
 }