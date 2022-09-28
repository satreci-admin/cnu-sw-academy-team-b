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
    @JoinColumn(name = "joblist_id")
    private JobListEntity jobListEntity;

    @NotNull
    @Column(length = 30)
    private String command;

    @Column(length = 30)
    private String parameter;

    private Boolean activation;

    @Builder
    public JobEntity(JobListEntity jobListEntity, String command, String parameter, Boolean activation) {
        this.jobListEntity = jobListEntity;
        this.command = command;
        this.parameter = parameter;
        this.activation = activation;
    }

    public void setCommand(String command) {
        this.command = command;
        this.jobListEntity.setUpdateAt(LocalDateTime.now());
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
        this.jobListEntity.setUpdateAt(LocalDateTime.now());
    }

    public void setActivation(Boolean activation) {
        this.activation = activation;
        this.jobListEntity.setUpdateAt(LocalDateTime.now());
    }

    public void setJobListEntity(JobListEntity jobListEntity) {
        if (Objects.nonNull(this.jobListEntity)) {
            this.jobListEntity.getJobEntityList().remove(this);
        }

        this.jobListEntity = jobListEntity;
        jobListEntity.getJobEntityList().add(this);
    }
}
