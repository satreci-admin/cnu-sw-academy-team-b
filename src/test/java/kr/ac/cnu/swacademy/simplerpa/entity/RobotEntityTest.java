package kr.ac.cnu.swacademy.simplerpa.entity;

import kr.ac.cnu.swacademy.simplerpa.repository.RobotRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class RobotEntityTest {

    @Autowired
    private RobotRepository robotRepository;

    @BeforeEach
    void setup() {
        RobotEntity robot = RobotEntity.builder().address("127.0.0.1:8080").password("1234").user("cnu").build();
        robotRepository.save(robot);
    }

    @AfterEach
    void cleanUp() {
        robotRepository.deleteAll();
    }
}