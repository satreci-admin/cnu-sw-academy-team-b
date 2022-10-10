package kr.ac.cnu.swacademy.simplerpa.service;

import kr.ac.cnu.swacademy.simplerpa.dto.RobotSaveRequestDto;
import kr.ac.cnu.swacademy.simplerpa.entity.RobotEntity;
import kr.ac.cnu.swacademy.simplerpa.repository.RobotRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class RobotServiceTest {

    @InjectMocks
    private RobotService robotService;

    @Mock
    private RobotRepository robotRepository;

    @Test
    void 로봇을_저장한다() {
        // given
        RobotSaveRequestDto dto = new RobotSaveRequestDto("127.0.0.1:22", "hello", "world");
        RobotEntity robotEntity = dto.toEntity();
        given(robotRepository.save(any(RobotEntity.class))).willReturn(robotEntity);

        // when
        robotService.save(dto);

        // then
        then(robotRepository).should().save(any(RobotEntity.class));
    }
}