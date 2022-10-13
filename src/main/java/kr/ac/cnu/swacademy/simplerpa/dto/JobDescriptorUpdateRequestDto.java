package kr.ac.cnu.swacademy.simplerpa.dto;

import kr.ac.cnu.swacademy.simplerpa.entity.JobDescriptorEntity;
import kr.ac.cnu.swacademy.simplerpa.entity.RobotEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class JobDescriptorUpdateRequestDto {
    private String name;
    private Long robotId;
    private Boolean isRepeat;
    private String executedDatetime;

    @Builder
    public JobDescriptorUpdateRequestDto(
            String name,
            Long robotId,
            Boolean isRepeat,
            String executedDatetime
    ) {
        this.name = name;
        this.robotId = robotId;
        this.isRepeat = isRepeat;
        this.executedDatetime = executedDatetime;
    }
}
