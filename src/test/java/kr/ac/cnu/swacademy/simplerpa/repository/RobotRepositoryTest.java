package kr.ac.cnu.swacademy.simplerpa.repository;

import kr.ac.cnu.swacademy.simplerpa.entity.RobotEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RobotRepositoryTest {

    @Autowired
    private RobotRepository robotRepository;

    @BeforeEach
    void setup() {}

    @AfterEach
    void cleanUp() {
        robotRepository.deleteAll();
    }

    @Test
    @Transactional
    void 로봇을_저장한다() {
        // given
        RobotEntity newRobotEntity = RobotEntity.builder()
                .address("127.0.0.1")
                .user("root")
                .password("1234")
                .build();

        // when
        RobotEntity saved = robotRepository.save(newRobotEntity);

        // then
        Optional<RobotEntity> foundById = robotRepository.findById(saved.getId());
        assertThat(foundById).isPresent();
        assertThat(foundById.get()).isEqualTo(saved);
    }

    @Test
    @Transactional
    void 로봇을_모두_조회한다() {
        // given
        RobotEntity newRobotEntity1 = RobotEntity.builder()
                .address("127.0.0.1")
                .user("root")
                .password("1234")
                .build();
        RobotEntity newRobotEntity2 = RobotEntity.builder()
                .address("127.0.0.1")
                .user("root")
                .password("1234")
                .build();
        // when
        RobotEntity saved1 = robotRepository.save(newRobotEntity1);
        RobotEntity saved2 = robotRepository.save(newRobotEntity2);

        List<RobotEntity> all = robotRepository.findAll();

        // then
        assertThat(all).hasSize(2);
        assertThat(all).contains(saved1);
        assertThat(all).contains(saved2);
    }

    @Test
    @Transactional
    void 로봇을_하나_조회한다() {
        // given
        RobotEntity newRobotEntity1 = RobotEntity.builder()
                .address("127.0.0.1")
                .user("root")
                .password("1234")
                .build();

        RobotEntity saved = robotRepository.save(newRobotEntity1);

        // when
        Optional<RobotEntity> foundRobot = robotRepository.findById(saved.getId());

        // then
        assertThat(foundRobot).isPresent();
        assertThat(foundRobot.get()).isEqualTo(saved);
    }

}