const form = document.querySelector('#form');
form.addEventListener('submit', handleFormSubmit);

async function handleFormSubmit(event) {

  event.preventDefault();
  const checkMemberPwd = document.querySelector('#check-memberPwd');
  checkMemberPwd.disabled;

  const form = event.currentTarget;
  const url = '/signup';

  try {

    const formData = new FormData(form);
    const responseData = await postFormDataAsJson({url, formData});

    console.log({responseData});
    
  } catch(error) {
    console.error(error);
  }
}

async function postFormDataAsJson({url, formData}) {
  const plainFormData = Object.fromEntries(formData.entries());
  const formDataJsonString = JSON.stringify(plainFormData);
  console.log(formDataJsonString);

  const fetchOptions = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Accept: 'application.json',
    },
    body: formDataJsonString,
  };

  fetch(url, fetchOptions)
  .then(response => {
    if (response.ok) {
      alert('가입을 축하드립니다. 로그인 페이지로 향합니다.');
      window.location.replace('/login');
      
    } else {
      alert('회원가입에 실패했습니다. 필수 항목을 기입해주시기 바랍니다.') ;
    }
  })
  .catch(error => console.log(error));
}
