package kr.ac.cnu.swacademy.simplerpa.entity;

import kr.ac.cnu.swacademy.simplerpa.repository.RobotRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class RobotEntityTest {

    @Autowired
    private RobotRepository robotRepository;


}