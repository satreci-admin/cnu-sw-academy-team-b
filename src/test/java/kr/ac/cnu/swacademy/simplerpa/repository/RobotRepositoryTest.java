package kr.ac.cnu.swacademy.simplerpa.repository;

import kr.ac.cnu.swacademy.simplerpa.entity.RobotEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RobotRepositoryTest {

    @Autowired
    private RobotRepository robotRepository;

    @BeforeEach
    void setup() {
        RobotEntity robotEntity = RobotEntity.builder()
                .address("127.0.0.1")
                .user("root")
                .password("1234")
                .build();
        robotRepository.save(robotEntity);
    }

    @AfterEach
    void cleanUp() {
        robotRepository.deleteAll();
    }

    @Test
    @Transactional
    void 로봇을_테이블에_저장한다() {
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
        Assertions.assertThat(foundById).isPresent();
        Assertions.assertThat(foundById.get()).isEqualTo(saved);
    }
}