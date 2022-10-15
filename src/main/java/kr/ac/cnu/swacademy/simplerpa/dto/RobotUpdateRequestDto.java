package kr.ac.cnu.swacademy.simplerpa.dto;

import kr.ac.cnu.swacademy.simplerpa.entity.RobotEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class RobotUpdateRequestDto {
    private String address;
    private String user;
    private String password;

    @Builder
    public RobotUpdateRequestDto (String address, String user, String password) {
        this.address = address;
        this.user = user;
        this.password = password;
    }

    public RobotEntity toEntity() {
        return RobotEntity
                .builder()
                .address(address)
                .user(user)
                .password(password)
                .build();
    }
}
