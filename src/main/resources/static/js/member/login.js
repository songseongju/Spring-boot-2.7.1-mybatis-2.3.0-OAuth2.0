const form = document.querySelector('#form'); //
form.addEventListener('submit', handleFormSubmit);  //작동함수

//form 데이터 한세트 

async function handleFormSubmit(event) {

  event.preventDefault();

  const form = event.currentTarget;
  const url = '/login';

  try {

    const formData = new FormData(form);
    const responseData = await postFormDataAsJson({ url, formData });

  } catch (error) {
    console.error(error);
  }
}


async function postFormDataAsJson({ url, formData }) {

  const plainFormData = Object.fromEntries(formData.entries()); 

  const formDataJsonString = JSON.stringify(plainFormData);        //html / form 데이터 불러오는거
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
  fetch(url, fetchOptions) // url , 각종옵션 바디 들어감 
    .then((response) =>
      response.json()      //백엔드 리퀘스트
    )
    .then((response) => {                  //백엔드 리스폰스  
      console.log(response.status);
      if (response.jwtToken) {
        sessionStorage.setItem('jwtToken', response.jwtToken);
        console.log(sessionStorage);
        alert('로그인 성공, 메인 페이지로 향합니다.');
        window.location.replace('/');
      } else {
        alert('요청하신 정보와 일치하는 계정이 존재하지 않습니다. Email 혹은 Password 를 확인 해주세요.');
        window.location.reload();
      }
  })


  function parseHttpHeaders(httpHeaders) {

    return httpHeaders
    
    .split("\n")
    
    .map(x => x.split(/: */, 2))
    
    .filter(x => x[0])
    
    .reduce((ac, x) => {
    
    ac[x[0]] = x[1];
    
    return ac;
    
    }, {});
    
    }
    
     
    
    var req = new XMLHttpRequest();
    
    req.open("GET", document.location, false);
    
    req.send(null);
    
    var headers = parseHttpHeaders(req.getAllResponseHeaders());
    
    console.log(headers);

    


  //여기까지

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
