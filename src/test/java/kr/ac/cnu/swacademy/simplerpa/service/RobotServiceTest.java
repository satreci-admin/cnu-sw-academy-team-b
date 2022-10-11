package kr.ac.cnu.swacademy.simplerpa.service;

import kr.ac.cnu.swacademy.simplerpa.dto.RobotListResponseDto;
import kr.ac.cnu.swacademy.simplerpa.dto.RobotResponseDto;
import kr.ac.cnu.swacademy.simplerpa.dto.RobotSaveRequestDto;
import kr.ac.cnu.swacademy.simplerpa.entity.RobotEntity;
import kr.ac.cnu.swacademy.simplerpa.repository.RobotRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
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

    @Test
    void 로봇을_전체조회한다() {
        // given
        RobotEntity robot1 = RobotEntity
                .builder()
                .address("100.100.100.100:100")
                .user("hi")
                .password("bye")
                .build();
        RobotEntity robot2 = RobotEntity
                .builder()
                .address("200.200.200.200:200")
                .user("hi")
                .password("bye")
                .build();
        given(robotRepository.findAll()).willReturn(List.of(robot1, robot2));

        // when
        List<RobotListResponseDto> all = robotService.findAll();

        // then
        then(robotRepository).should().findAll();
        assertThat(all).hasSize(2);
        assertThat(all.get(0))
                .usingRecursiveComparison()
                .isEqualTo(new RobotListResponseDto(robot1));
        assertThat(all.get(1))
                .usingRecursiveComparison()
                .isEqualTo(new RobotListResponseDto(robot2));
    }

    @Test
    void 로봇을_단건조회한다() {
        // given
        RobotEntity robot1 = RobotEntity
                .builder()
                .address("100.100.100.100:100")
                .user("hi")
                .password("bye")
                .build();
        given(robotRepository.findById(any(Long.class))).willReturn(Optional.of(robot1));

        // when
        RobotResponseDto responseDto = robotService.findById(1L);

        // then
        then(robotRepository).should().findById(1L);
        assertThat(new RobotResponseDto(robot1))
                .usingRecursiveComparison()
                .isEqualTo(responseDto);
    }
}