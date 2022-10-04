package kr.ac.cnu.swacademy.simplerpa.repository;

import kr.ac.cnu.swacademy.simplerpa.entity.JobDescriptorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobDescriptorRepository extends JpaRepository<JobDescriptorEntity, Long> {
}
