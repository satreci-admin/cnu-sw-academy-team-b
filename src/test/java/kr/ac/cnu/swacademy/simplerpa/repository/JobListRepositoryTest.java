package kr.ac.cnu.swacademy.simplerpa.repository;

import kr.ac.cnu.swacademy.simplerpa.entity.JobListEntity;
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
class JobListRepositoryTest {

    @Autowired
    JobListRepository jobListRepository;

    @AfterEach
    void tearDown() {
        jobListRepository.deleteAll();
    }

    @Test
    void 작업명세서를_저장한다() {
        // Given
        JobListEntity jobListEntity = JobListEntity.builder()
                .name("작업명세서1")
                .isRepeat(Boolean.FALSE)
                .build();

        // When
        jobListRepository.save(jobListEntity);

        // Then
        Optional<JobListEntity> selectedJobListEntity = jobListRepository.findById(jobListEntity.getId());
        assertThat(selectedJobListEntity).isPresent();
        assertThat(selectedJobListEntity.get().getId()).isEqualTo(jobListEntity.getId());
    }

    @Test
    void 모든_작업명세서를_조회한다() {
        // Given
        JobListEntity jobListEntity1 = JobListEntity.builder()
                .name("작업명세서1")
                .isRepeat(Boolean.FALSE)
                .build();
        JobListEntity jobListEntity2 = JobListEntity.builder()
                .name("작업명세서2")
                .isRepeat(Boolean.FALSE)
                .build();
        jobListRepository.saveAll(Lists.newArrayList(jobListEntity1, jobListEntity2));

        // When
        List<JobListEntity> jobListEntities = jobListRepository.findAll();

        // Then
        assertThat(jobListEntities).hasSize(2);
    }

    @Test
    void 작업명세서를_단건조회한다() {
        // Given
        JobListEntity jobListEntity = JobListEntity.builder()
                .name("작업명세서1")
                .isRepeat(Boolean.FALSE)
                .build();

        jobListRepository.save(jobListEntity);

        // When
        Optional<JobListEntity> selectedJobListEntity = jobListRepository.findById(jobListEntity.getId());

        // Then
        assertThat(selectedJobListEntity).isPresent();
        assertThat(selectedJobListEntity.get().getId()).isEqualTo(jobListEntity.getId());
    }

    @Test
    void 작엽명세서를_수정한다() {
        // Given
        JobListEntity jobListEntity = JobListEntity.builder()
                .name("작업명세서1")
                .isRepeat(Boolean.FALSE)
                .build();
        JobListEntity savedJobListEntity = jobListRepository.save(jobListEntity);

        // When
        savedJobListEntity.setName("수정된 작업명세서1");

        // Then
        Optional<JobListEntity> selectedJobListEntity = jobListRepository.findById(jobListEntity.getId());
        assertThat(selectedJobListEntity).isPresent();
        assertThat(selectedJobListEntity.get().getId()).isEqualTo(savedJobListEntity.getId());
        assertThat(selectedJobListEntity.get().getName()).isEqualTo(savedJobListEntity.getName());
    }

    @Test
    void 작업명세서를_삭제한다() {
        // Given
        JobListEntity jobListEntity = JobListEntity.builder()
                .name("작업명세서1")
                .isRepeat(Boolean.FALSE)
                .build();
        jobListRepository.save(jobListEntity);

        // When
        jobListRepository.deleteById(jobListEntity.getId());

        // Then
        List<JobListEntity> jobListEntities = jobListRepository.findAll();
        assertThat(jobListEntities).isEmpty();
    }
}