package kr.ac.cnu.swacademy.simplerpa.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "job_lists")
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

    @NotNull
    private Boolean repeat;

    private LocalDateTime excutedDatetime;

    @Builder
    public JobListEntity(String name, Boolean repeat,RobotEntity robotEntity, LocalDateTime excuted_datetime) {
        this.name = name;
        this.repeat = repeat;
        this.robotEntity = robotEntity;
        this.excutedDatetime = excuted_datetime;
    }

    public void setName(String name) {
        this.name = name;
        this.setUpdateAt(LocalDateTime.now());
    }

    public void setRobotEntity(RobotEntity robotEntity) {
        this.robotEntity = robotEntity;
        this.setUpdateAt(LocalDateTime.now());
    }

    public void setRepeat(Boolean repeat) {
        this.repeat = repeat;
        this.setUpdateAt(LocalDateTime.now());
    }

    public void setExcutedDatetime(LocalDateTime excutedDatetime) {
        this.excutedDatetime = excutedDatetime;
        this.setUpdateAt(LocalDateTime.now());
    }
}
