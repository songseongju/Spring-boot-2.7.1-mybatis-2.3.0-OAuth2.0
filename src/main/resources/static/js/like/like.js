
// * 서버에 요청 시, artworkId, memberId 파라미터, 상황에 맞게 하는 방법? ${}?, Object param?

// * 비회원 == 토큰이 없는 유저
// * 회원 == 토큰 발급 받은 유저 (로그인을 통해)

// uri '/artwork/{artworkId}' 에 GET 요청이 들어오면,
// 페이지 로드와 함께, 유저 토큰이 있는지 확인한다.

// 토큰이 없는 경우, 좋아요 기능은 사용 불가.
// (비 회원이 좋아요 버튼 클릭 시, alert('로그인 이후 이용하실 수 있습니다.'))

// 토큰이 있는 경우, 해당 유저가 해당 작품에 대한 좋아요를 가지고 있는 지 확인한다. hasLike();

// hasLike() data가 0 인 경우
// document.querySelector('#like-button').value = '좋아요';


// hasLike() data가 1 인 경우
// document.querySelector('#like-button').value = '좋아요 취소';


// 토큰이 있는 경우, 유저가 좋아요 버튼 클릭 시,

// 좋아요 버튼 value 가 '좋아요' 인 경우,
// like();

// 좋아요 버튼 value 가 '좋아요 취소' 인 경우,
// unlike();

// 해당 artwork 업로드한 멤버 아이디 추출.
const uploader = document.querySelector('#uploader').value;
console.log(uploader + ' is a uploader Id');

// 해당 artworkId 추출.
const artworkId = document.querySelector('#artworkId').value;
console.log(artworkId + ' is a artworkId')

const likeButton = document.querySelector('#like-button');


if (sessionStorage.jwtToken) {

  window.addEventListener('load', function () {
    likeButtonValue();
    getLikeCount();
  })

  likeButton.addEventListener('click', async function () {
    likeOrUnlike();
  })

  // jwtToken 디코드
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

  var userId = decodedJWT.memberId;
  console.log(userId + ' is a userId');

  // 라이크 버튼 value 결정 하는 함수. 해당 작품에 대한 로그인 멤버의 좋아요 이력에 따른 value 결정.
  function likeButtonValue() {
    fetch(`/hasLike/${artworkId}/${userId}`, {
      headers: {
        'Authorization': 'Bearer ' + sessionStorage.jwtToken,
      }
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data + ' the result of GET request hasLike() from likeButtonValue().');
        if (parseInt(data) === 0) {
          buttonValueLike();
          console.log('the member has no like on the artwork. from likeButtonValue().')
        } else {
          buttonValueUnlike();
          console.log('the member has a like on the artwork. from likeButtonValue().')
        }
      })
  }

  function likeOrUnlike() {
    fetch(`/hasLike/${artworkId}/${userId}`, {
      headers: {
        'Authorization': 'Bearer ' + sessionStorage.jwtToken,
      }
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data + ' the result of GET request hasLike(). from likeOrUnlike().');
        if (parseInt(data) === 0) {
          like();
        } else {
          unlike();
        }
      });
  }
  
  function like() {
    // console.log('Bearer ' + sessionStorage.jwtToken);
    fetch('/like', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + sessionStorage.jwtToken,
      },
      body: JSON.stringify({
        'artworkId': `${artworkId}`,
        'memberId': `${userId}`,
      })
    })
      .then((response) => {
        console.log(response.text());
        likeButton.value = '좋아요 취소';
      })
      .then(() => {
        alert('해당 작품에 좋아요를 했습니다.')
        getLikeCount();
      })
      .catch((error) => console.error(error));
  }

  function unlike() {
    fetch(`/unlike/${artworkId}/${userId}`, {
      method: 'DELETE',
      headers: {
        'Authorization': 'Bearer ' + sessionStorage.jwtToken,
      }
    })
      .then((response) => {
        console.log(response);
        likeButton.value = '좋아요';
      })
      .then(() => {
        alert('해당 작품에 좋아요를 취소했습니다.')
        getLikeCount();
      })
      .catch((error) => console.error(error));

  }

  function buttonValueLike() {
    likeButton.value = '좋아요';
  }

  function buttonValueUnlike() {
    likeButton.value = '좋아요 취소';
  }

}

function getLikeCount() {
  fetch(`/likeCount/${artworkId}`)
    .then((response) => response.json())
    .then((data) => {
      document.querySelector('#likeCount').innerHTML = '좋아요 ' + data + ' 회';
      console.log(data + ' The number is the like counts for this artwork. from getLikeCount()');
    });
}
