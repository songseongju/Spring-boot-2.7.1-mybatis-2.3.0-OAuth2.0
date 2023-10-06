package com.team.togethart.dto.member;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown=true)
@Data
public class KakaoProfile {

    public Long id;
    public String connected_at;
    public Properties properties;
    public KakaoAccount kakao_account;

    @Data
    public class Properties {

        public String nickname;
      public String profileImage;
      public String thumbnailImage;
    }


@Data
public class KakaoAccount {

   public Boolean profile_nicknameNeeds_agreement;
   public Boolean profile_imageNeeds_agreement;

    public Profile profile;
    public Boolean has_email;
    public Boolean email_needs_agreement;
    public Boolean is_email_valid;
    public Boolean is_email_verified;

    public String email;

    @Data
    public class Profile {

        public String nickname;
        public String thumbnail_image_url;
        public String profileImage_url;


     //   public Boolean isDefault_image;

    }

 }

}


