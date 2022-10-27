package kr.ac.cnu.swacademy.simplerpa.dto;

import kr.ac.cnu.swacademy.simplerpa.entity.JobDescriptorEntity;
import lombok.Getter;

@Getter
public class JobDescriptorListResponseDto {
    private Long id;
    private String name;
    private Long robotId;

    public JobDescriptorListResponseDto(JobDescriptorEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.robotId = entity.getRobotEntity() != null ? entity.getRobotEntity().getId() : null;
    }
}
