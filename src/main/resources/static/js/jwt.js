$(document).ready(function() {
  const loginForm = document.getElementById("login-form");

  loginForm.addEventListener("submit", async function(event) {
    event.preventDefault(); // 기본 폼 제출 동작을 막음

    const userId = document.getElementById("userId").value;
    const password = document.getElementById("password").value;

    try {
      console.log('Sending login request with:', { userId, password });

      const response = await fetch('/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ userId, password })
      });

      console.log('Response received:', response);

      if (response.ok) {
        const responseData = await response.json();
        console.log('Parsed response data:', responseData);

        const token = responseData.token;
        if (token) {
          localStorage.setItem('jwtToken', token);
          console.log('Token stored in local storage:', token);

          // 로그인 성공 후 리다이렉션
          window.location.href = '/main'; // 로그인 후 이동할 페이지
        } else {
          console.error('Token not found in response');

        }
      } else {
        console.error('Login failed:', response.statusText);

      }
    } catch (error) {
      console.error('Error:', error);
      alert('로그인 중 오류가 발생했습니다. 다시 시도해주세요.');
    }
  });
});
