package kr.ac.cnu.swacademy.simplerpa.repository;

import kr.ac.cnu.swacademy.simplerpa.entity.JobDescriptorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JobDescriptorRepository extends JpaRepository<JobDescriptorEntity, Long> {
    @Query("SELECT j FROM JobDescriptorEntity j ORDER BY j.id DESC")
    List<JobDescriptorEntity> findAllDesc();

    List<JobDescriptorEntity> findAllByExecutedDatetimeBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT j FROM JobDescriptorEntity j where j.robotEntity.id = :id")
    List<JobDescriptorEntity> findJobDescriptorEntitiesByRobotEntity(Long id);
}

