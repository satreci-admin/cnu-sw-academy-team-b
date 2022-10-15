package kr.ac.cnu.swacademy.simplerpa.dto;

import kr.ac.cnu.swacademy.simplerpa.entity.JobEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class JobListResponseDto {

    private Long id;
    private Long jobDescriptorEntityId;
    private String command;
    private String parameter;
    private Boolean activation;

    public JobListResponseDto(JobEntity jobEntity)
    {
        this.id = jobEntity.getId();
        this.jobDescriptorEntityId = jobEntity.getJobDescriptorEntity().getId();
        this.command = jobEntity.getCommand();
        this.parameter = jobEntity.getParameter();
        this.activation = jobEntity.getActivation();
    }




}

