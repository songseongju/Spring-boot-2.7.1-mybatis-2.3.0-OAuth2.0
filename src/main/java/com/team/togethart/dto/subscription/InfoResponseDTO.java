package com.team.togethart.dto.subscription;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class InfoResponseDTO {


    private String memberUsername;
    private LocalDateTime startDate;
    private LocalDateTime expireDate;


}
