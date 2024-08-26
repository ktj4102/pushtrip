function openModal(modalId) {
	    document.getElementById(modalId).style.display = "block";
	}

	// 모달 닫기
	function closeModal(modalId) {
	    document.getElementById(modalId).style.display = "none";
	}

	// 모달 외부 클릭 시 모달 닫기
	window.onclick = function(event) {
	    if (event.target.classList.contains('modal-name')) {
	        event.target.style.display = "none";
	    }

	    if (event.target.classList.contains('modal-phone')) {
	        event.target.style.display = "none";
	    }

	    if (event.target.classList.contains('modal-email')) {
	        event.target.style.display = "none";
	    }

	    if (event.target.classList.contains('modal-birth')) {
	        event.target.style.display = "none";
	    }

	    if (event.target.classList.contains('modal-id')) {
	        event.target.style.display = "none";
	    }

	    if (event.target.classList.contains('modal-password')) {
	        event.target.style.display = "none";
	    }

	    if (event.target.classList.contains('modal-photo')) {
	        event.target.style.display = "none";
	    }

	    if (event.target.classList.contains('modal-addr')) {
	        event.target.style.display = "none";
	    }
	}

	function toggleHiddenContent() {
        var content = document.getElementById("hiddenContent");
        if (content.style.display === "none") {
            content.style.display = "block";
        } else {
            content.style.display = "none";
        }
    }
    //전화번호 유효성
    function validateForm() {
        var telInput = document.getElementById('tel');
        var errorMessage = document.getElementById('error-message');
        var tel = telInput.value.trim();

        // 전화번호 유효성 검사: 숫자만 허용, 10자리에서 15자리까지 허용
        var phonePattern = /^\d{10,15}$/;

        if (!phonePattern.test(tel)) {
            errorMessage.style.display = 'block';
            return false; // 폼 제출 중지
        } else {
            errorMessage.style.display = 'none';
            return true; // 폼 제출 허용
        }
    }

    function closeModal(modalId) {
        document.getElementById(modalId).style.display = 'none';
    }

    function validateEmailForm() {
        var emailInput = document.getElementById('email');
        var errorMessage = document.getElementById('email-error-message');
        var email = emailInput.value.trim();

        // 이메일 유효성 검사: 일반적인 이메일 형식 체크
        var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if (!emailPattern.test(email)) {
            errorMessage.style.display = 'block';
            return false; // 폼 제출 중지
        } else {
            errorMessage.style.display = 'none';
            return true; // 폼 제출 허용
        }
    }

    function validateBirthForm() {
        const birthInput = document.getElementById('birth');
        const errorMessage = document.getElementById('birth-error-message');
        const birth = birthInput.value.trim();

        // 생년월일 패턴 정의 (YYYY-MM-DD)
        const birthPattern = /^\d{4}-\d{2}-\d{2}$/;

        // 생년월일 형식 검사
        if (!birthPattern.test(birth)) {
            errorMessage.textContent = '생년월일은 YYYY-MM-DD 형식으로 입력해주세요.';
            errorMessage.style.display = 'block';
            return false; // 폼 제출 중지
        }

        // 생년월일이 실제로 존재하는 날짜인지 검사
        const [year, month, day] = birth.split('-').map(Number);
        const date = new Date(year, month - 1, day);

        if (date.getFullYear() !== year || date.getMonth() !== month - 1 || date.getDate() !== day) {
            errorMessage.textContent = '유효한 날짜를 입력해주세요.';
            errorMessage.style.display = 'block';
            return false; // 폼 제출 중지
        }

        // 생년월일이 유효한 경우
        errorMessage.textContent = '';
        errorMessage.style.display = 'none';
        return true; // 폼 제출 허용
    }

    function validateAddressForm() {
        const roadDetail = document.getElementById('roadDetail').value.trim();
        const errorMessage = document.getElementById('address-error-message');

        // 상세주소 입력 여부 확인
        if (roadDetail === '') {
            errorMessage.textContent = '상세주소를 입력해주세요.';
            errorMessage.style.display = 'block';
            return false; // 폼 제출 중지
        } else {
            errorMessage.style.display = 'none';
            return true; // 폼 제출 허용
        }
    }

    //탈퇴
    function confirmDelete() {
        return confirm('정말 탈퇴하시겠습니까?');
    }
