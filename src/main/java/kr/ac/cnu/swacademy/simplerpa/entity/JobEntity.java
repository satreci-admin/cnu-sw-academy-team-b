package kr.ac.cnu.swacademy.simplerpa.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Table(name="jobs")
public class JobEntity {

    @Id
    @Column(name = "job_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jobdescriptor_id")
    private JobDescriptorEntity jobDescriptorEntity;

    @NotNull
    @Column(length = 30)
    private String command;

    @Column(length = 30)
    private String parameter;

    private Boolean activation;

    @Builder
    public JobEntity(JobDescriptorEntity jobDescriptorEntity, String command, String parameter, Boolean activation) {
        this.jobDescriptorEntity = jobDescriptorEntity;
        this.command = command;
        this.parameter = parameter;
        this.activation = activation;
    }

    public void setCommand(String command) {
        this.command = command;
        this.jobDescriptorEntity.setUpdateAt(LocalDateTime.now());
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
        this.jobDescriptorEntity.setUpdateAt(LocalDateTime.now());
    }

    public void setActivation(Boolean activation) {
        this.activation = activation;
        this.jobDescriptorEntity.setUpdateAt(LocalDateTime.now());
    }

    public void setJobDescriptorEntity(JobDescriptorEntity jobDescriptorEntity) {
        if (Objects.nonNull(this.jobDescriptorEntity)) {
            this.jobDescriptorEntity.getJobEntityList().remove(this);
        }

        this.jobDescriptorEntity = jobDescriptorEntity;
        jobDescriptorEntity.getJobEntityList().add(this);
    }
}
