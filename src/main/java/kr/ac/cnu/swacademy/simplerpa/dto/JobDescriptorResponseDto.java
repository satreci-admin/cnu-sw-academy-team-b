package kr.ac.cnu.swacademy.simplerpa.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.ac.cnu.swacademy.simplerpa.entity.JobDescriptorEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class JobDescriptorResponseDto {

    private Long id;
    private String name;
    private Long robotId;
    private Boolean isRepeat;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime executedDateTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    public JobDescriptorResponseDto(JobDescriptorEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.robotId = entity.getRobotEntity() != null ? entity.getRobotEntity().getId() : null;
        this.isRepeat = entity.getIsRepeat();
        this.executedDateTime = entity.getExecutedDatetime();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdateAt();
    }
}
