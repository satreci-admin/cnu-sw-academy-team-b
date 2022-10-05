package kr.ac.cnu.swacademy.simplerpa.entity;

import kr.ac.cnu.swacademy.simplerpa.repository.JobDescriptorRepository;
import kr.ac.cnu.swacademy.simplerpa.repository.JobRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JobEntityTest {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    JobDescriptorRepository jobDescriptorRepository;

    private JobEntity jobEntity;
    private JobDescriptorEntity jobDescriptorEntity;

    @BeforeEach
    void beforeEach() {
    }

    @AfterEach
    void afterEach() {

    }

    @Test
    @DisplayName("작업을 저장한다")
    @Transactional
    void insert() {

        JobEntity jobEntity = JobEntity.builder()
                .command("ls")
                .parameter("-al")
                .activation(Boolean.FALSE)
                .build();

        jobRepository.save(jobEntity);

        JobEntity jobEntity1 = jobRepository.findAll().get(0);

        assertThat(jobEntity1.getCommand()).isEqualTo(jobEntity.getCommand());

    }

    @Test
    @DisplayName("작업을 하나 조회한다")
    @Transactional
    void select() {

        JobEntity jobEntity = JobEntity.builder()
                .command("ls")
                .parameter("-al")
                .jobDescriptorEntity(jobDescriptorEntity)
                .activation(Boolean.FALSE)
                .build();

        JobEntity savedJobEntity = jobRepository.save(jobEntity);

        JobEntity jobEntity1 = jobRepository.findAll().get(0);

        assertThat(jobEntity1.getCommand()).isEqualTo(savedJobEntity.getCommand());

    }

    @Test
    @DisplayName("작업을 모두 조회한다")
    @Transactional
    void select_all() {

        JobEntity jobEntity1 = JobEntity.builder()
                .command("ls")
                .parameter("-al")
                .jobDescriptorEntity(jobDescriptorEntity)
                .activation(Boolean.FALSE)
                .build();

        JobEntity jobEntity2 = JobEntity.builder()
                .command("cp")
                .parameter("-r")
                .jobDescriptorEntity(jobDescriptorEntity)
                .activation(Boolean.FALSE)
                .build();

        JobEntity savedJobEntity1 = jobRepository.save(jobEntity1);
        JobEntity savedJobEntity2 = jobRepository.save(jobEntity2);

        List<JobEntity> jobEntities = jobRepository.findAll();

        assertThat(jobEntities).hasSize(2);
        assertThat(jobEntities.get(0).getCommand()).isEqualTo(jobEntity1.getCommand());
        assertThat(jobEntities.get(1).getCommand()).isEqualTo(jobEntity2.getCommand());


    }

    @Test
    @DisplayName("작업을 수정한다")
    @Transactional
    void update() {

        JobDescriptorEntity jobDescriptorEntity = JobDescriptorEntity.builder()
                .name("hello")
                .isRepeat(Boolean.FALSE)
                .build();

        JobEntity jobEntity = JobEntity.builder()
                .command("ls")
                .parameter("-al")
                .jobDescriptorEntity(jobDescriptorEntity)
                .activation(Boolean.FALSE)
                .build();

        jobDescriptorRepository.save(jobDescriptorEntity);
        JobEntity savedJobEntity = jobRepository.save(jobEntity);


        savedJobEntity.setCommand("cp");


        Optional<JobEntity> jobEntity1 = jobRepository.findById(savedJobEntity.getId());

        assertThat(jobEntity1).isPresent();
        assertThat(jobEntity1.get().getCommand()).isEqualTo(savedJobEntity.getCommand());

    }

    @Test
    @DisplayName("작업을 삭제한다")
    @Transactional
    void delete() {

        JobEntity jobEntity = JobEntity.builder()
                .command("ls")
                .parameter("-al")
                .jobDescriptorEntity(jobDescriptorEntity)
                .activation(Boolean.FALSE)
                .build();

        JobEntity savedJobEntity = jobRepository.save(jobEntity);


        jobRepository.delete(savedJobEntity);

        List<JobEntity> jobEntities = jobRepository.findAll();

        assertThat(jobEntities).isEmpty();

    }
}