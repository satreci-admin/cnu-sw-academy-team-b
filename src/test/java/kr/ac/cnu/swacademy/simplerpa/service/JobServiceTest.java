package kr.ac.cnu.swacademy.simplerpa.service;

import kr.ac.cnu.swacademy.simplerpa.dto.JobListResponseDto;
import kr.ac.cnu.swacademy.simplerpa.entity.JobDescriptorEntity;
import kr.ac.cnu.swacademy.simplerpa.entity.JobEntity;
import kr.ac.cnu.swacademy.simplerpa.entity.RobotEntity;
import kr.ac.cnu.swacademy.simplerpa.repository.JobDescriptorRepository;
import kr.ac.cnu.swacademy.simplerpa.repository.JobRepository;
import kr.ac.cnu.swacademy.simplerpa.repository.RobotRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JobServiceTest {

    @Autowired
    JobService jobService;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    JobDescriptorRepository jobDescriptorRepository;
    @Autowired
    RobotRepository robotRepository;

    @BeforeEach
    void setup()
    {
        jobRepository.deleteAll();
        jobDescriptorRepository.deleteAll();
        robotRepository.deleteAll();
    }
    @AfterEach
    void cleanup()
    {
//        jobRepository.deleteAll();
    }

    @Test
    void 작업을_조회한다()
    {
        //given
        RobotEntity robotEntity = RobotEntity.builder()
                .address("127.0.0.1")
                .user("root")
                .password("1234")
                .build();

        JobDescriptorEntity jobDescriptorEntity = JobDescriptorEntity.builder()
                .name("hello")
                .robotEntity(robotEntity)
                .isRepeat(Boolean.FALSE)
                .build();

        JobEntity jobEntity = JobEntity.builder()
                .command("ls")
                .parameter("-al")
                .jobDescriptorEntity(jobDescriptorEntity)
                .activation(Boolean.FALSE)
                .build();

        robotRepository.save(robotEntity);
        jobDescriptorRepository.save(jobDescriptorEntity);
        JobEntity savedJobEntity = jobRepository.save(jobEntity);

        //when
        Optional<JobListResponseDto> foundJobResponseDto = jobService.findById(savedJobEntity.getId());

        //then
        assertThat(foundJobResponseDto).isPresent();

    }
    @Test
    void 작업을_모두_조회한다()
    {
        //given
        JobDescriptorEntity jobDescriptorEntity1 = JobDescriptorEntity.builder()
                .name("hello")
                .isRepeat(Boolean.FALSE)
                .build();

        JobDescriptorEntity jobDescriptorEntity2 = JobDescriptorEntity.builder()
                .name("hi")
                .isRepeat(Boolean.FALSE)
                .build();

        JobEntity jobEntity1 = JobEntity.builder()
                .command("ls")
                .parameter("-al")
                .jobDescriptorEntity(jobDescriptorEntity1)
                .activation(Boolean.FALSE)
                .build();

        JobEntity jobEntity2 = JobEntity.builder()
                .command("cp")
                .parameter("-r")
                .jobDescriptorEntity(jobDescriptorEntity2)
                .activation(Boolean.FALSE)
                .build();

        jobDescriptorRepository.save(jobDescriptorEntity1);
        jobDescriptorRepository.save(jobDescriptorEntity2);
        JobEntity savedJobEntity1 = jobRepository.save(jobEntity1);
        JobEntity savedJobEntity2 = jobRepository.save(jobEntity2);

        //when
        List<JobListResponseDto> jobListResponseDtos = jobService.findByJobDescriptorEntity(jobDescriptorEntity1.getId());

        //then
        assertThat(jobListResponseDtos).hasSize(1);
        assertThat(jobListResponseDtos.get(0).getId())
                .isEqualTo(savedJobEntity1.getId());
        assertThat(jobListResponseDtos.get(0).getJobDescriptorEntityId())
                .isEqualTo(savedJobEntity1.getJobDescriptorEntity().getId());
    }

}