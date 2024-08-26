$(document).ready(function() {
    const accessToken = localStorage.getItem('accessToken');

    // accessToken이 없으면 로그인 페이지로 리디렉션
    if (!accessToken) {
      alert("로그인이 필요합니다.");
      window.location.href = '/loginForm';
    }
  });