package kr.ac.cnu.swacademy.simplerpa.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "job_descriptors")
@Getter
public class JobDescriptorEntity extends BaseTimeEntity{

    @Id
    @Column(name = "jobdescriptor_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 30)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "robot_id")
    private RobotEntity robotEntity;

    @NotNull
    private Boolean isRepeat;

    private LocalDateTime executedDatetime;

    @OneToMany(mappedBy = "jobDescriptorEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    List<JobEntity> jobEntityList = new ArrayList<>();

    @Builder
    public JobDescriptorEntity(String name, Boolean isRepeat, RobotEntity robotEntity, LocalDateTime executedDatetime) {
        this.name = name;
        this.isRepeat = isRepeat;
        this.robotEntity = robotEntity;
        this.executedDatetime = executedDatetime;
    }

    public void setName(String name) {
        this.name = name;
        this.setUpdateAt(LocalDateTime.now());
    }

    public void setRobotEntity(RobotEntity robotEntity) {
        this.robotEntity = robotEntity;
        this.setUpdateAt(LocalDateTime.now());
    }

    public void setIsRepeat(Boolean isRepeat) {
        this.isRepeat = isRepeat;
        this.setUpdateAt(LocalDateTime.now());
    }

    public void setExecutedDatetime(LocalDateTime executedDatetime) {
        this.executedDatetime = executedDatetime;
        this.setUpdateAt(LocalDateTime.now());
    }

    public void addJobEntity(JobEntity jobEntity) {
        jobEntity.setJobDescriptorEntity(this);
    }
}
