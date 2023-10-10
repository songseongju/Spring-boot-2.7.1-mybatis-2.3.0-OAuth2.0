const form = document.querySelector('#form');
form.addEventListener('submit', handleFormSubmit);

async function handleFormSubmit(event) {

  event.preventDefault();

  const form = event.currentTarget;
  const url = '/login/findPwd';

  try {

    const formData = new FormData(form);
    const responseData = await postFormDataAsJson({ url, formData });

  } catch (error) {
    console.error(error);
  }
}

async function postFormDataAsJson({ url, formData }) {
  const plainFormData = Object.fromEntries(formData.entries());
  const formDataJsonString = JSON.stringify(plainFormData);

  // console.log(formDataJsonString + ' postFormDataAsJson() formDataJsonString');

  
  const fetchOptions = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      
    },
    body: formDataJsonString,
  };

  // fetch 했을때, 백엔드 api 에서 응담상태에 따라 바디에 담는 타입이 Map, String 으로 각각 다른 경우의 처리 방법 필요.

  fetch(url, fetchOptions)
    .then((response) =>
      response.json()
      
    )
    .then((response) => {
      console.log(response.status);
      if (response.jwtToken) {
        sessionStorage.setItem('jwtToken', response.jwtToken);
        console.log(sessionStorage);
        alert('이메일 찾기 성공하셨습니다');
        window.location.replace('/');
      } else {
        alert('요청하신 정보와 일치하는 유저네임이 존재하지 않습니다. 유저네임을 다시 확인 해주세요.');
        window.location.reload();
      }
  })

  // fetch(url, fetchOptions)
  //   .then((response) => {
  //     response.json()
  //   })
  //   .then((response) => {
  //     console.log(response.jwtToken);
  //   })



  // alert('요청하신 정보와 일치하는 계정이 존재하지 않습니다. Email 혹은 Password 를 확인 해주세요.');
  // window.location.reload();

  // .catch(error => alert(error))
}
