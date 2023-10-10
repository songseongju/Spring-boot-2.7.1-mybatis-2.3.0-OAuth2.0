const signupButton = document.querySelector('#signup-button');
const loginButton = document.querySelector('#login-button');
const logoutButton = document.querySelector('#logout-button');
const uploadButton = document.querySelector('#upload-button');
const myPageButton = document.querySelector('#myPage-button');

// 세션 토큰 유무에 따라 버튼의 숨김 변경.
if (sessionStorage.jwtToken) {
  signupButton.style.display = 'none';
  loginButton.style.display = 'none';

  const token = sessionStorage.getItem('jwtToken')?.replace('Bearer ', '');
  // console.log(token);
  const base64Payload = token.split('.')[1];
  const base64 = base64Payload.replace(/-/g, '+').replace(/_/g, '/');
  const decodedJWT = JSON.parse(
    decodeURIComponent(
      window
        .atob(base64)
        .split('')
        .map(function (c) {
          return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        })
        .join('')
    )
  );
  myPageButton.href = '/member/' + decodedJWT.memberId;
} else {
  logoutButton.style.display = 'none';
  uploadButton.style.display = 'none';
  myPageButton.style.display = 'none';
}



//로그아웃
logoutButton.addEventListener('click', logout);

function logout() {
  sessionStorage.removeItem('jwtToken');
}