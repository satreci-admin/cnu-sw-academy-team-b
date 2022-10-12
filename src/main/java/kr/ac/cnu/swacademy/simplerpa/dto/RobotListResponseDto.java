package kr.ac.cnu.swacademy.simplerpa.dto;

import kr.ac.cnu.swacademy.simplerpa.entity.RobotEntity;

public class RobotListResponseDto {
    private String address;
    private Long id;

    public RobotListResponseDto(RobotEntity robotEntity) {
        this.address = robotEntity.getAddress();
        this.id = robotEntity.getId();
    }
}
