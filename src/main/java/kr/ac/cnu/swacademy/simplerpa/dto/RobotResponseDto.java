package kr.ac.cnu.swacademy.simplerpa.dto;

import kr.ac.cnu.swacademy.simplerpa.entity.RobotEntity;

public class RobotResponseDto {
    private Long id;
    private String address;
    private String user;
    private String password;

    public RobotResponseDto(RobotEntity robotEntity) {
        this.id = robotEntity.getId();
        this.address = robotEntity.getAddress();
        this.user = robotEntity.getUser();
        this.password = robotEntity.getPassword();
    }
}
