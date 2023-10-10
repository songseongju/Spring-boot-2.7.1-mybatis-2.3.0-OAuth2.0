
      // 파일 입력(input type="file) 요소와 미리보기 이미지 요소를 가져옵니다.
      const fileInput = document.getElementById('fileInput');
      const imagePreview = document.getElementById('imagePreview');
      const previewImage = document.getElementById('preview');

      // 파일 선택 시 미리보기 함수를 설정합니다.
      fileInput.addEventListener('change', function() {
          const file = fileInput.files[0];

          if (file) {
              const reader = new FileReader();

              // 파일을 읽어와서 미리보기 이미지에 표시합니다.
              reader.onload = function(e) {
                  previewImage.src = e.target.result;
                  imagePreview.style.display = 'block'; // 미리보기 영역을 표시합니다.
              };

              reader.readAsDataURL(file);
          } else {
              // 파일이 선택되지 않았을 때, 미리보기 이미지를 숨깁니다.
              imagePreview.style.display = 'none';
          }
      });
