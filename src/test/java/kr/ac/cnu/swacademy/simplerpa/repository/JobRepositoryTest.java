package kr.ac.cnu.swacademy.simplerpa.repository;

import kr.ac.cnu.swacademy.simplerpa.entity.JobDescriptorEntity;
import kr.ac.cnu.swacademy.simplerpa.entity.JobEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JobRepositoryTest {

    @Autowired
    JobRepository jobRepository;
    @Autowired
    JobDescriptorRepository jobDescriptorRepository;

    @Test
    @Transactional
    void 작업을_저장한다() {
        //given
        JobEntity jobEntity = JobEntity.builder()
                .command("ls")
                .parameter("-al")
                .activation(Boolean.FALSE)
                .build();

        JobEntity savedJobEntity = jobRepository.save(jobEntity);

        //when
        Optional<JobEntity> foundedJobEntity = jobRepository.findById(jobEntity.getId());

        //then
        assertThat(foundedJobEntity.get()).isEqualTo(savedJobEntity);

    }

    @Test
    @Transactional
    void 작업을_하나_조회한다() {

        //given
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

        //when
        Optional<JobEntity> foundedJobEntity = jobRepository.findById(jobEntity.getId());

        //then
        assertThat(foundedJobEntity.get()).isEqualTo(savedJobEntity);

    }

    @Test
    @Transactional
    void 작업을_모두_조회한다() {

        //given
        JobDescriptorEntity jobDescriptorEntity = JobDescriptorEntity.builder()
                .name("hello")
                .isRepeat(Boolean.FALSE)
                .build();

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

        jobDescriptorRepository.save(jobDescriptorEntity);
        JobEntity savedJobEntity1 = jobRepository.save(jobEntity1);
        JobEntity savedJobEntity2 = jobRepository.save(jobEntity2);

        //when
        List<JobEntity> jobEntities = jobRepository.findAll();

        //then
        assertThat(jobEntities).hasSize(2);
        assertThat(jobEntities.get(0)).isEqualTo(savedJobEntity1);
        assertThat(jobEntities.get(1)).isEqualTo(savedJobEntity2);


    }

    @Test
    @Transactional
    void 작업을_수정한다() {

        //given
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

        //when
        savedJobEntity.setCommand("cp");

        //then
        assertThat(jobEntity).isEqualTo(savedJobEntity);
        assertThat(jobEntity.getCommand()).isEqualTo(savedJobEntity.getCommand());

    }

    @Test
    @Transactional
    void 작업을_삭제한다() {

        //given
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

        JobEntity savedJobEntity = jobRepository.save(jobEntity);

        //when
        jobRepository.delete(savedJobEntity);

        //then
        List<JobEntity> jobEntities = jobRepository.findAll();

        assertThat(jobEntities).isEmpty();
    }
}