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
@Table(name = "job_lists")
@Getter
public class JobListEntity extends BaseTimeEntity{

    @Id
    @Column(name = "joblist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 30)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "robot_id")
    private RobotEntity robotEntity;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean isRepeat;

    private LocalDateTime excutedDatetime;

    @OneToMany(mappedBy = "jobListEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    List<JobEntity> jobEntityList = new ArrayList<>();

    @Builder
    public JobListEntity(String name, Boolean isRepeat, RobotEntity robotEntity, LocalDateTime excuted_datetime) {
        this.name = name;
        this.isRepeat = isRepeat;
        this.robotEntity = robotEntity;
        this.excutedDatetime = excuted_datetime;
    }

    public void setName(String name) {
        this.name = name;
        this.setUpdateAt(LocalDateTime.now());
    }

    public void setRobotEntity(RobotEntity robotEntity) {
        if (Objects.nonNull(this.robotEntity)) {
            this.robotEntity.getJobListEntities().remove(this);
        }

        this.robotEntity = robotEntity;
        robotEntity.getJobListEntities().add(this);
        this.setUpdateAt(LocalDateTime.now());
    }

    public void setIsRepeat(Boolean isRepeat) {
        this.isRepeat = isRepeat;
        if(Boolean.FALSE.equals(isRepeat)) this.excutedDatetime = null;
        this.setUpdateAt(LocalDateTime.now());
    }

    public void setExcutedDatetime(LocalDateTime excutedDatetime) {
        this.excutedDatetime = excutedDatetime;
        this.setUpdateAt(LocalDateTime.now());
    }

    public void addJobEntity(JobEntity jobEntity) {
        jobEntity.setJobListEntity(this);
    }
}
