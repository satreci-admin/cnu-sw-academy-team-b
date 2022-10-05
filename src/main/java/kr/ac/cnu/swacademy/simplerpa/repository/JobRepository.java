package kr.ac.cnu.swacademy.simplerpa.repository;

import kr.ac.cnu.swacademy.simplerpa.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, Long> {
}