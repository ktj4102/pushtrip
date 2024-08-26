$(document).ready(function() {
  const profileContainer = $('#profileContainer');
  const accessToken = localStorage.getItem('accessToken');
  const refreshToken = getCookie('refreshToken');
  const mypageLink = $('.btn-profile[href="/mypage"]'); // 마이페이지 링크 요소 선택
  const messageIcon = $('a[href="javascript:void(0)"][onclick="openMessagePopup()"]'); // 쪽지함 아이콘 요소 선택

  if (accessToken && refreshToken) {
    // 토큰이 존재할 경우 로그아웃 버튼으로 변경
    profileContainer.html(`
      <a href="javascript:void(0)" class="btn-profile header-utils-btn" id="logoutButton" style="font-weight: bold; margin-top: 10px; font-size: 17px;">
          Logout
        </a>
    `);

    // 로그아웃 기능 추가
    $('#logoutButton').on('click', handleLogout);

    // 마이페이지 링크를 보이게 하기 (필요한 경우)
    mypageLink.show();

    // 쪽지함 아이콘 보이기
    messageIcon.show();
  } else {
    // 토큰이 없을 경우 로그인 버튼으로 변경
    profileContainer.html(`
      <a href="/loginForm" class="btn-profile header-utils-btn" style="font-weight: bold; margin-top: 10px; font-size: 17px;">Login</a>
    `);

    // 마이페이지 링크를 숨기기
    mypageLink.hide();

    // 쪽지함 아이콘 숨기기
    messageIcon.hide();
  }
});

function handleLogout() {
  // localStorage에서 토큰 삭제
  localStorage.removeItem('accessToken');

  // refreshToken 쿠키 삭제
  deleteCookie('refreshToken');

  // 서버에 로그아웃 요청
  $.ajax({
    url: '/logout',
    type: 'POST',
    xhrFields: {
      withCredentials: true
    },
    success: function() {
      // 로그아웃 후 페이지 새로고침

      window.location.href = '/main';
    },
    error: function() {
      console.error('Logout failed');
    }
  });
}

function getCookie(name) {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${name}=`);
  if (parts.length === 2) return parts.pop().split(';').shift();
}

function deleteCookie(name) {
  document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
}
