window.addEventListener('load', () => {
  getMainGalleryForNotMember();

})

// function getNavigation() {
//   const navWrapper = document.querySelector('#nav-wrapper')

// }


// fetch api 로 받아온 객체를 파라미터로 하는 갤러리 함수.
function list(data) {

  for (i = 0; i < data.length; i++) {

    const artworkId = data[i].artworkId;
    const resourcePathname = data[i].resourcePathname;

    const container = document.querySelector('#gallery-container');
    const anchor = document.createElement('a');
    const wrapper = document.createElement('div');
    const piece = document.createElement('img');

    wrapper.className = 'gallery-item';
    anchor.href = '/artwork/' + artworkId;
    piece.src = resourcePathname;

    container.appendChild(anchor);
    anchor.appendChild(wrapper);
    wrapper.appendChild(piece);

  }

}

// 토큰 여부에 따라서 함수 사용 구분
function getMainGalleryForNotMember() {
  const url = '/main/index'
  fetch(url)
    .then(response => response.json())
    .then(data => {
      console.log(data);
      list(data);

    })
    .catch(e => console.error(e));
}

// 현재 로그인 한 회원의 토큰 기반 정보 획득
const token = sessionStorage.getItem('jwtToken')?.replace('Bearer ', '');
console.log(token);
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

// 
console.log(decodedJWT);
console.log(decodedJWT.memberId);



// DOM HTML 엘레멘트 생성 예시.
// function example() {
//   {
//     const container = document.querySelector('#gallery-container');
//     const anchor = document.createElement('a');
//     const wrapper = document.createElement('div');
//     const piece = document.createElement('img');

//     console.log(container, anchor, wrapper, piece);

//     wrapper.className = 'gallery-item';
//     anchor.href = "/artwork/1";
//     piece.src = '/imgs/image3.png';

//     container.appendChild(anchor);
//     anchor.appendChild(wrapper);
//     wrapper.appendChild(piece);
//   }
// }