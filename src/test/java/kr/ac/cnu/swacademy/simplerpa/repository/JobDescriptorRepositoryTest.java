package kr.ac.cnu.swacademy.simplerpa.repository;

import kr.ac.cnu.swacademy.simplerpa.entity.JobDescriptorEntity;
import kr.ac.cnu.swacademy.simplerpa.entity.RobotEntity;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class JobDescriptorRepositoryTest {

    @Autowired
    JobDescriptorRepository jobDescriptorRepository;

    @Autowired
    RobotRepository robotRepository;

    @AfterEach
    void tearDown() {
        jobDescriptorRepository.deleteAll();
    }

    @Test
    void 작업명세서를_저장한다() {
        // Given
        JobDescriptorEntity jobDescriptorEntity = JobDescriptorEntity.builder()
                .name("작업명세서1")
                .isRepeat(Boolean.FALSE)
                .build();

        // When
        jobDescriptorRepository.save(jobDescriptorEntity);

        // Then
        Optional<JobDescriptorEntity> selectedJobListEntity = jobDescriptorRepository.findById(jobDescriptorEntity.getId());
        assertThat(selectedJobListEntity).isPresent();
        assertThat(selectedJobListEntity.get().getId()).isEqualTo(jobDescriptorEntity.getId());
    }

    @Test
    void 모든_작업명세서를_조회한다() {
        // Given
        JobDescriptorEntity jobDescriptorEntity1 = JobDescriptorEntity.builder()
                .name("작업명세서1")
                .isRepeat(Boolean.FALSE)
                .build();
        JobDescriptorEntity jobDescriptorEntity2 = JobDescriptorEntity.builder()
                .name("작업명세서2")
                .isRepeat(Boolean.FALSE)
                .build();
        jobDescriptorRepository.saveAll(Lists.newArrayList(jobDescriptorEntity1, jobDescriptorEntity2));

        // When
        List<JobDescriptorEntity> jobListEntities = jobDescriptorRepository.findAll();

        // Then
        assertThat(jobListEntities).hasSize(2);
    }

    @Test
    void 작업명세서를_단건조회한다() {
        // Given
        JobDescriptorEntity jobDescriptorEntity = JobDescriptorEntity.builder()
                .name("작업명세서1")
                .isRepeat(Boolean.FALSE)
                .build();

        jobDescriptorRepository.save(jobDescriptorEntity);

        // When
        Optional<JobDescriptorEntity> selectedJobListEntity = jobDescriptorRepository.findById(jobDescriptorEntity.getId());

        // Then
        assertThat(selectedJobListEntity).isPresent();
        assertThat(selectedJobListEntity.get().getId()).isEqualTo(jobDescriptorEntity.getId());
    }

    @Test
    void 특정_로봇에서_실행되는_모든_작업명세서를_조회한다() {
        // Given
        RobotEntity robotEntity1 = RobotEntity.builder()
                .address("127.0.0.1:22")
                .user("anonymous")
                .password("1234")
                .build();
        RobotEntity robotEntity2 = RobotEntity.builder()
                .address("192.168.0.1:22")
                .user("anonymous")
                .password("1234")
                .build();
        robotRepository.saveAll(List.of(robotEntity1, robotEntity2));

        JobDescriptorEntity jobDescriptorEntity1 = JobDescriptorEntity.builder()
                .name("작업명세서1")
                .isRepeat(Boolean.FALSE)
                .robotEntity(robotEntity1)
                .build();
        JobDescriptorEntity jobDescriptorEntity2 = JobDescriptorEntity.builder()
                .name("작업명세서2")
                .isRepeat(Boolean.FALSE)
                .robotEntity(robotEntity1)
                .build();
        JobDescriptorEntity jobDescriptorEntity3 = JobDescriptorEntity.builder()
                .name("작업명세서3")
                .isRepeat(Boolean.FALSE)
                .robotEntity(robotEntity2)
                .build();
        jobDescriptorRepository.saveAll(List.of(jobDescriptorEntity1, jobDescriptorEntity2, jobDescriptorEntity3));

        // When
        List<JobDescriptorEntity> jobDescriptorEntitiesByRobotEntity1 = jobDescriptorRepository.findJobDescriptorEntitiesByRobotEntity(robotEntity1.getId());

        // Then
        assertThat(jobDescriptorEntitiesByRobotEntity1).hasSize(2);
    }

    @Test
    void 작엽명세서를_수정한다() {
        // Given
        JobDescriptorEntity jobDescriptorEntity = JobDescriptorEntity.builder()
                .name("작업명세서1")
                .isRepeat(Boolean.FALSE)
                .build();
        JobDescriptorEntity savedJobDescriptorEntity = jobDescriptorRepository.save(jobDescriptorEntity);

        // When
        savedJobDescriptorEntity.setName("수정된 작업명세서1");

        // Then
        Optional<JobDescriptorEntity> selectedJobListEntity = jobDescriptorRepository.findById(jobDescriptorEntity.getId());
        assertThat(selectedJobListEntity).isPresent();
        assertThat(selectedJobListEntity.get().getId()).isEqualTo(savedJobDescriptorEntity.getId());
        assertThat(selectedJobListEntity.get().getName()).isEqualTo(savedJobDescriptorEntity.getName());
    }

    @Test
    void 작업명세서를_삭제한다() {
        // Given
        JobDescriptorEntity jobDescriptorEntity = JobDescriptorEntity.builder()
                .name("작업명세서1")
                .isRepeat(Boolean.FALSE)
                .build();
        jobDescriptorRepository.save(jobDescriptorEntity);

        // When
        jobDescriptorRepository.deleteById(jobDescriptorEntity.getId());

        // Then
        Optional<JobDescriptorEntity> gotJobDescriptorEntity = jobDescriptorRepository.findById(jobDescriptorEntity.getId());
        assertThat(gotJobDescriptorEntity).isEmpty();
    }
}