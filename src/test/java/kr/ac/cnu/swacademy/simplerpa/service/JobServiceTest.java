package kr.ac.cnu.swacademy.simplerpa.service;

import kr.ac.cnu.swacademy.simplerpa.dto.JobListResponseDto;
import kr.ac.cnu.swacademy.simplerpa.dto.JobSaveRequestDto;
import kr.ac.cnu.swacademy.simplerpa.dto.JobUpdateRequestDto;
import kr.ac.cnu.swacademy.simplerpa.entity.JobDescriptorEntity;
import kr.ac.cnu.swacademy.simplerpa.entity.JobEntity;
import kr.ac.cnu.swacademy.simplerpa.repository.JobDescriptorRepository;
import kr.ac.cnu.swacademy.simplerpa.repository.JobRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class JobServiceTest {

    @InjectMocks
    JobService jobService;

    @Mock
    JobRepository jobRepository;

    @Mock
    JobDescriptorRepository jobDescriptorRepository;

    @Test
    void 작업을_조회한다()
    {
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

        Long id = 1L;

        given(jobRepository.findById(id)).willReturn(Optional.of(jobEntity));

        //when
        Optional<JobListResponseDto> foundJobResponseDto = jobService.findById(id);

        //then
        verify(jobRepository).findById(id);
        assertThat(foundJobResponseDto.get().getCommand()).isEqualTo(jobEntity.getCommand());
        assertThat(foundJobResponseDto.get().getActivation()).isEqualTo(jobEntity.getActivation());
        assertThat(foundJobResponseDto.get().getJobDescriptorEntityId()).isEqualTo(jobEntity.getJobDescriptorEntity().getId());
        assertThat(foundJobResponseDto.get().getId()).isEqualTo(jobEntity.getId());
    }

    @Test
    void 작업을_조회할때_입력id의_작업이_없는_경우_예외_테스트()
    {

        Long id = 1L;

        given(jobRepository.findById(id)).willReturn(Optional.empty());

        //when, then
        Assertions.assertThatThrownBy(() -> jobService.findById(id))
                        .isInstanceOf(IllegalArgumentException.class);
        verify(jobRepository).findById(id);
    }

    @Test
    void 작업을_모두_조회한다()
    {
        //given
        JobDescriptorEntity jobDescriptorEntity1 = JobDescriptorEntity.builder()
                .name("hello")
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
                .jobDescriptorEntity(jobDescriptorEntity1)
                .activation(Boolean.FALSE)
                .build();

        Long id = 1L;

        given(jobRepository.findByJobDescriptorEntity(id)).willReturn(List.of(jobEntity1, jobEntity2));

        //when
        List<JobListResponseDto> jobListResponseDtos = jobService.findByJobDescriptorEntity(id);

        //then
        verify(jobRepository).findByJobDescriptorEntity(id);
        assertThat(jobListResponseDtos).hasSize(2);
        assertThat(jobListResponseDtos.get(0).getId())
                .isEqualTo(jobEntity1.getId());
        assertThat(jobListResponseDtos.get(0).getJobDescriptorEntityId())
                .isEqualTo(jobEntity1.getJobDescriptorEntity().getId());
        assertThat(jobListResponseDtos.get(1).getId())
                .isEqualTo(jobEntity2.getId());
        assertThat(jobListResponseDtos.get(1).getJobDescriptorEntityId())
                .isEqualTo(jobEntity2.getJobDescriptorEntity().getId());
    }

    @Test
    void 작업을_생성한다()
    {

        JobDescriptorEntity jobDescriptorEntity1 = JobDescriptorEntity.builder()
                .name("작업명세서1")
                .isRepeat(false)
                .build();
        JobEntity jobEntity1 = JobEntity.builder()
                .command("ls")
                .parameter("-al")
                .jobDescriptorEntity(jobDescriptorEntity1)
                .activation(Boolean.FALSE)
                .build();
        JobSaveRequestDto jobSaveRequestDto = JobSaveRequestDto.builder()
                .command("ls")
                .parameter("-al")
                .jobDescriptionId(1L)
                .activation(Boolean.FALSE)
                .build();

        //given
        given(jobDescriptorRepository.findById(anyLong())).willReturn(Optional.of(jobDescriptorEntity1));
        given(jobRepository.save(any(JobEntity.class))).willReturn(jobEntity1);

        //when
        jobService.create(jobSaveRequestDto);

        //then
        verify(jobRepository).save(any(JobEntity.class));
        verify(jobDescriptorRepository).findById(anyLong());
    }

    @Test
    void 작업을_생성할때_입력id의_작업명세서가_없는_경우_예외_테스트()
    {
        //given
        JobSaveRequestDto jobSaveRequestDto = JobSaveRequestDto.builder()
                .command("ls")
                .parameter("-al")
                .jobDescriptionId(1L)
                .activation(Boolean.FALSE)
                .build();

        Long id = 1L;
        given(jobDescriptorRepository.findById(id)).willReturn(Optional.empty());

        //when, then
        Assertions.assertThatThrownBy(() -> jobService.create(jobSaveRequestDto))
                .isInstanceOf(IllegalArgumentException.class);
        verify(jobDescriptorRepository).findById(id);

    }

    @Test
    void 작업을_수정한다()
    {

        JobDescriptorEntity jobDescriptorEntity1 = JobDescriptorEntity.builder()
                .name("작업명세서1")
                .isRepeat(false)
                .build();
        JobEntity jobEntity1 = JobEntity.builder()
                .command("ls")
                .parameter("-al")
                .jobDescriptorEntity(jobDescriptorEntity1)
                .activation(Boolean.FALSE)
                .build();

        JobUpdateRequestDto jobUpdateRequestDto = JobUpdateRequestDto.builder()
                .command("cp")
                .parameter("-rm")
                .jobDescriptionId(1L)
                .activation(Boolean.FALSE)
                .build();

        Long id = 1L;

        //given
        given(jobRepository.findById(id)).willReturn(Optional.of(jobEntity1));
        //when
        Long updatedId = jobService.update(jobUpdateRequestDto, id);

        //then
        verify(jobRepository).findById(id);
        assertThat(updatedId).isEqualTo(jobEntity1.getId());
        assertThat(jobEntity1.getCommand()).isEqualTo(jobUpdateRequestDto.getCommand());
        assertThat(jobEntity1.getParameter()).isEqualTo(jobUpdateRequestDto.getParameter());
        assertThat(jobEntity1.getActivation()).isEqualTo(jobUpdateRequestDto.getActivation());
    }

    @Test
    void 작업을_수정할때_입력id의_작업이_없는_경우_예외_테스트()
    {

        //given
        JobUpdateRequestDto jobUpdateRequestDto = JobUpdateRequestDto.builder()
                .command("cp")
                .parameter("-rm")
                .jobDescriptionId(1L)
                .activation(Boolean.FALSE)
                .build();
        Long id = 1L;
        given(jobRepository.findById(id)).willReturn(Optional.empty());

        //when, then
        Assertions.assertThatThrownBy(() -> jobService.update(jobUpdateRequestDto, id))
                .isInstanceOf(IllegalArgumentException.class);
        verify(jobRepository).findById(id);

    }

    @Test
    void 작업을_삭제한다()
    {

        JobDescriptorEntity jobDescriptorEntity1 = JobDescriptorEntity.builder()
                .name("작업명세서1")
                .isRepeat(false)
                .build();

        JobEntity jobEntity1 = JobEntity.builder()
                .command("ls")
                .parameter("-al")
                .jobDescriptorEntity(jobDescriptorEntity1)
                .activation(Boolean.FALSE)
                .build();

        Long id = 1L;

        //given
        given(jobRepository.findById(id)).willReturn(Optional.of(jobEntity1));

        //when
        jobService.delete(id);

        //then
        verify(jobRepository).findById(id);
    }

}