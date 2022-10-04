package kr.ac.cnu.swacademy.simplerpa.repository;

import kr.ac.cnu.swacademy.simplerpa.entity.JobDescriptorEntity;
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
        List<JobDescriptorEntity> jobListEntities = jobDescriptorRepository.findAll();
        assertThat(jobListEntities).isEmpty();
    }
}