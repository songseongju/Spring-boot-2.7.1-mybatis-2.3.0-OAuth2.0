document.addEventListener("DOMContentLoaded", function () {
  const token = sessionStorage.getItem("jwtToken")?.replace("Bearer ", "");
  // console.log(token);

  // 토큰이 존재하면 해독하여 memberId를 추출
  if (token) {
    const base64Payload = token.split(".")[1];
    const base64 = base64Payload.replace(/-/g, "+").replace(/_/g, "/");
    const decodedJWT = JSON.parse(
      decodeURIComponent(
        window
          .atob(base64)
          .split("")
          .map(function (c) {
            return "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2);
          })
          .join("")
      )
    );

    var subscriptionButton = document.getElementById("subscription-button");
    var isSubscribing = false;
    var subscribeFrom = decodedJWT.memberId; // 나
    var subscribeTo = document.getElementById("uploader").value; // 대상

    updatesubscriptionButton();

    subscriptionButton.addEventListener("click", function (event) {
      event.preventDefault();

      if (isSubscribing) {
        // 현재 팔로우 중인 상태인 경우
        unsubscribeUser();
      } else {
        subscribeUser();
      }
    });

    function updatesubscriptionButton() {
      // 서버에 팔로우 관계 확인 요청 보내기
      fetch(
        `/sub/sub-check?subscribeFrom=${subscribeFrom}&subscribeTo=${subscribeTo}`
      )
        .then(function (response) {
          if (response.ok) {
            return response.json();
          } else {
            console.error("요청 실패");
          }
        })
        .then(function (data) {
          var isSubscriptionExists = data.isSubscriptionExists;

          // 팔로우 버튼 업데이트
          if (isSubscriptionExists) {
            subscriptionButton.innerHTML = "구독취소";
          } else {
            subscriptionButton.innerHTML = "구독하기";
          }

          isSubscribing = isSubscriptionExists;
        })
        .catch(function (error) {
          console.error("오류 발생:", error);
        });
    }

    // 팔로우
    function subscribeUser() {
      var requestData = {
        subscribeFrom: subscribeFrom,
        subscribeTo: subscribeTo,
      };

      fetch("/sub/makesub", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(requestData),
      })
        .then(function (response) {
          if (response.ok) {
            isSubscribing = true;
            updatesubscriptionButton();
          } else {
            console.error("요청 실패");
          }
        })
        .catch(function (error) {
          console.error("오류 발생:", error);
        });
    }

    // 언팔로우
    function unsubscribeUser() {
      var requestData = {
        subscribeFrom: subscribeFrom,
        subscribeTo: subscribeTo,
      };

      fetch("/sub/deletesub", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(requestData),
      })
        .then(function (response) {
          if (response.ok) {
            isSubscribing = false;
            updatesubscriptionButton();
          } else {
            console.error("요청 실패");
          }
        })
        .catch(function (error) {
          console.error("오류 발생:", error);
        });
    }
  }
});
