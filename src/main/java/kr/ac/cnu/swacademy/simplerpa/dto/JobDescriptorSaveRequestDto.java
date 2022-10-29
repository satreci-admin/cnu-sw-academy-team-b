package kr.ac.cnu.swacademy.simplerpa.dto;

import kr.ac.cnu.swacademy.simplerpa.entity.JobDescriptorEntity;
import kr.ac.cnu.swacademy.simplerpa.entity.RobotEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Getter
@Slf4j
@NoArgsConstructor
public class JobDescriptorSaveRequestDto {
    private String name;
    private Long robotId;
    private Boolean isRepeat;
    private String executedDatetime;

    @Builder
    public JobDescriptorSaveRequestDto(
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

    public JobDescriptorEntity toEntity(RobotEntity robotEntity) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime executedLocalDatetime = (Objects.isNull(executedDatetime) || Objects.equals(executedDatetime, "")) ? null : LocalDateTime.parse(executedDatetime, formatter);

        return JobDescriptorEntity.builder()
                .name(name)
                .robotEntity(robotEntity)
                .executedDatetime(executedLocalDatetime)
                .isRepeat(isRepeat)
                .build();
    }
}
