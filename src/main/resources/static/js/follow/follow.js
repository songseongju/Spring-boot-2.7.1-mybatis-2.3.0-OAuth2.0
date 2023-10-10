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

    var followButton = document.getElementById("follow-button");
    var isFollowing = false;
    var followFrom = decodedJWT.memberId; // 나
    var followTo = document.getElementById("uploader").value; // 대상

    updateFollowButton();

    followButton.addEventListener("click", function (event) {
      event.preventDefault();

      if (isFollowing) {
        // 현재 팔로우 중인 상태인 경우
        unfollowUser();
      } else {
        followUser();
      }
    });

    function updateFollowButton() {
      // 서버에 팔로우 관계 확인 요청 보내기
      fetch(
        `/follow/follow-check?followFrom=${followFrom}&followTo=${followTo}`
      )
        .then(function (response) {
          if (response.ok) {
            return response.json();
          } else {
            console.error("요청 실패");
          }
        })
        .then(function (data) {
          var isFollowExists = data.isFollowExists;

          // 팔로우 버튼 업데이트
          if (isFollowExists) {
            followButton.innerHTML = "언팔로우";
          } else {
            followButton.innerHTML = "팔로우";
          }

          isFollowing = isFollowExists;
        })
        .catch(function (error) {
          console.error("오류 발생:", error);
        });
    }

    // 팔로우
    function followUser() {
      var requestData = {
        followFrom: followFrom,
        followTo: followTo,
      };

      fetch("/follow/makefollow", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(requestData),
      })
        .then(function (response) {
          if (response.ok) {
            isFollowing = true;
            updateFollowButton();
          } else {
            console.error("요청 실패");
          }
        })
        .catch(function (error) {
          console.error("오류 발생:", error);
        });
    }

    // 언팔로우
    function unfollowUser() {
      var requestData = {
        followFrom: followFrom,
        followTo: followTo,
      };

      fetch("/follow/unfollow", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(requestData),
      })
        .then(function (response) {
          if (response.ok) {
            isFollowing = false;
            updateFollowButton();
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
