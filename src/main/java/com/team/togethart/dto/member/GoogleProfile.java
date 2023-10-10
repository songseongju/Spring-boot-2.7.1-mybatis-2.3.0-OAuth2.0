package com.team.togethart.dto.member;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GoogleProfile {

    private String sub;
    private String name;

    private String email;
    private String picture;

    @Builder
    public GoogleProfile(String sub , String name , String email){
        this.sub = sub;
        this.name = name;
        this.email = email;

    }

    public MemberAddRequest toEntity(){
        return MemberAddRequest.builder()
                .memberEmail(email+sub)
                .memberUsername(name)
                .build();
    }



}
