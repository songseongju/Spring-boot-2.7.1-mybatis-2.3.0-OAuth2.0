const form = document.querySelector('#form'); //
form.addEventListener('submit', handleFormSubmit);  //작동함수


//form 데이터 한세트 

async function handleFormSubmit(event) {

  event.preventDefault();

  const form = event.currentTarget;
  try {

    const formData = new FormData(form);
    const responseData = await postFormDataAsJson({formData});

  } catch (error) {
    console.error(error);
  }
}

async function postFormDataAsJson({formData}) {

  const plainFormData = Object.fromEntries(formData.entries()); 

  const fetchOptions = {

    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',

    }
    ,body: 
      JSON.stringify({
      
        memberUsername : {memberUsername}

    }),

  };

  fetch('/login/findEmail', fetchOptions)
    .then((response) =>
      response.json()  //백엔드 리퀘스트
    )
    .then((result) => {  //백엔드 리스폰스

                     
      if (result.message === 'KEY_ERROR') {
       
        alert('요청하신 정보와 일치하는 유저네임이 존재하지 않습니다. 유저네임을 확인 해주세요.');

        window.location.reload();

      } else {

        alert('현재 사용자 Email 은 ' + response.memberEmail + '입니다.')

        window.location.replace('/');
      
      }
  })

  
}
