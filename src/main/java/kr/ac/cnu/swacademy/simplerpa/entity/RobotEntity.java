package kr.ac.cnu.swacademy.simplerpa.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "robots")
public class RobotEntity extends BaseTimeEntity {

    @Id
    @Column(name = "robot_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 30)
    private String address;
    @NotNull
    @Column(length = 30)
    private String user;
    @NotNull
    @Column(length = 30)
    private String password;

    @Builder
    public RobotEntity(String address, String user, String password) {
        this.address = address;
        this.user = user;
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
        this.setUpdateAt(LocalDateTime.now());
    }

    public void setUser(String user) {
        this.user = user;
        this.setUpdateAt(LocalDateTime.now());
    }

    public void setPassword(String password) {
        this.password = password;
        this.setUpdateAt(LocalDateTime.now());
    }
}
