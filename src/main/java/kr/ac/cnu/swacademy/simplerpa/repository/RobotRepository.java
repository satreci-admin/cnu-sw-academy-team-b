package kr.ac.cnu.swacademy.simplerpa.repository;

import kr.ac.cnu.swacademy.simplerpa.entity.RobotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RobotRepository extends JpaRepository<RobotEntity, Long> {
}
