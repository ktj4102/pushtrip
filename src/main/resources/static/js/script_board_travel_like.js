// 작성자 : 민욱
// URL에서 travelId 값 가져오기
var urlCommParams = new URLSearchParams(window.location.search);
var travelId = urlCommParams.get('travelId');

// travelId가 제대로 가져와졌는지 확인
console.log("Travel ID for js:", travelId);

// 페이지 로드 시 좋아요 수 초기화
updateLikeCount(travelId);

// 좋아요 버튼 클릭 시 호출되는 함수
function addLike() {
  console.log("addLike 버튼 클릭!");

  // 입력된 userId 값 가져오기
  var userId = document.getElementById('userId').value;

  if (!userId) {
      alert("회원 ID를 입력하세요.");
      return;
  }

  // 좋아요 요청 보내기
  fetch(`/travelForumSelect/${travelId}/like?userId=${encodeURIComponent(userId)}`, {
      method: 'POST'
  })
  .then(response => {
      if (response.ok) {
          return response.text();
      } else {
          throw new Error('Failed to add like');
      }
  })
  .then(text => {
      if(text === "Liked") {
        console.log(text);
        // 좋아요 수 업데이트
        updateLikeCount(travelId);
        alert("추천!");
      }
  })
  .catch(error => {
      console.error('Error:', error);
  });
}

// 좋아요 수를 서버에서 가져와서 업데이트하는 함수
function updateLikeCount(travelId) {
  fetch(`/travelForumSelect/${travelId}/likeCount`)
      .then(response => response.json())
      .then(count => {
          document.getElementById('likeCount').textContent = count;
      })
      .catch(error => {
          console.error('Error:', error);
      });
}