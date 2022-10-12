package kr.ac.cnu.swacademy.simplerpa.dto;

import kr.ac.cnu.swacademy.simplerpa.entity.JobDescriptorEntity;
import kr.ac.cnu.swacademy.simplerpa.entity.JobEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JobUpdateRequestDto {

    private String command;
    private String parameter;
    private Boolean activation;
    private Long jobDescriptionId;

    @Builder
    public JobUpdateRequestDto(String command, String parameter, Boolean activation, Long jobDescriptionId)
    {
        this.command = command;
        this.parameter = parameter;
        this.activation = activation;
        this.jobDescriptionId = jobDescriptionId;
    }

    public JobEntity toEntity(JobDescriptorEntity jobDescriptorEntity)
    {
        return JobEntity.builder()
                .command(command)
                .jobDescriptorEntity(jobDescriptorEntity)
                .parameter(parameter)
                .activation(activation)
                .build();
    }
}
