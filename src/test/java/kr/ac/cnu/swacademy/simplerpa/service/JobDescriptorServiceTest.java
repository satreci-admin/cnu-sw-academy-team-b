package kr.ac.cnu.swacademy.simplerpa.service;

import kr.ac.cnu.swacademy.simplerpa.dto.JobDescriptorListResponseDto;
import kr.ac.cnu.swacademy.simplerpa.dto.JobDescriptorResponseDto;
import kr.ac.cnu.swacademy.simplerpa.dto.JobDescriptorUpdateRequestDto;
import kr.ac.cnu.swacademy.simplerpa.entity.JobDescriptorEntity;
import kr.ac.cnu.swacademy.simplerpa.entity.RobotEntity;
import kr.ac.cnu.swacademy.simplerpa.repository.JobDescriptorRepository;
import kr.ac.cnu.swacademy.simplerpa.repository.RobotRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;


@ExtendWith(MockitoExtension.class)
class JobDescriptorServiceTest {

    @Mock
    JobDescriptorRepository jobDescriptorRepository;
    @Mock
    RobotRepository robotRepository;

    @InjectMocks
    JobDescriptorService jobDescriptorService;

    @Test
    @DisplayName("작업명세서 리스트 조회")
    void findAllDescTest() {
        // Given
        JobDescriptorEntity jobDescriptorEntity1 = JobDescriptorEntity.builder()
                .name("작업명세서1")
                .isRepeat(false)
                .build();
        JobDescriptorEntity jobDescriptorEntity2 = JobDescriptorEntity.builder()
                .name("작업명세서2")
                .isRepeat(false)
                .build();
        given(jobDescriptorRepository.findAllDesc()).willReturn(List.of(jobDescriptorEntity1, jobDescriptorEntity2));

        // When
        List<JobDescriptorListResponseDto> jobDescriptorListResponseDtos = jobDescriptorService.findAllDesc();

        // Then
        then(jobDescriptorRepository).should().findAllDesc();

        assertThat(jobDescriptorListResponseDtos).hasSize(2);
        assertThat(jobDescriptorListResponseDtos.get(0)).usingRecursiveComparison().isEqualTo(new JobDescriptorListResponseDto(jobDescriptorEntity1));
        assertThat(jobDescriptorListResponseDtos.get(1)).usingRecursiveComparison().isEqualTo(new JobDescriptorListResponseDto(jobDescriptorEntity2));
    }


    @Test
    @DisplayName("작업명세서 상세 조회")
    void findByIdTest() {
        // Given
        Long id = 1L;
        JobDescriptorEntity jobDescriptorEntity = JobDescriptorEntity.builder()
                .name("작업명세서1")
                .isRepeat(false)
                .build();
        given(jobDescriptorRepository.findById(anyLong())).willReturn(Optional.ofNullable(jobDescriptorEntity));

        // When
        Optional<JobDescriptorResponseDto> jobDescriptorResponseDto = jobDescriptorService.findById(id);

        // Then
        then(jobDescriptorRepository).should().findById(anyLong());

        assertThat(jobDescriptorResponseDto).isPresent();
        assertThat(jobDescriptorResponseDto.get()).usingRecursiveComparison().isEqualTo(new JobDescriptorResponseDto(jobDescriptorEntity));
    }

    @Test
    @DisplayName("작업명세서 상세 조회 - 존재하지 않을 때")
    void findById_InvalidId_ReturnEmptyOptionalTest() {
        // Given
        given(jobDescriptorRepository.findById(anyLong())).willReturn(Optional.empty());

        // When
        Optional<JobDescriptorResponseDto> jobDescriptorResponseDto = jobDescriptorService.findById(anyLong());

        // Then
        then(jobDescriptorRepository).should().findById(anyLong());

        assertThat(jobDescriptorResponseDto).isEmpty();
    }

    @Test
    @DisplayName("작업명세서 수정")
    void updateTest() {
        // Given
        RobotEntity robotEntity1 = RobotEntity.builder()
                .address("127.0.0.1:22")
                .user("anonymous")
                .password("1234")
                .build();
        JobDescriptorEntity jobDescriptorEntity = JobDescriptorEntity.builder()
                .name("작업명세서")
                .isRepeat(false)
                .robotEntity(robotEntity1)
                .build();
        given(jobDescriptorRepository.findById(anyLong())).willReturn(Optional.of(jobDescriptorEntity));

        JobDescriptorUpdateRequestDto jobDescriptorUpdateRequestDto = JobDescriptorUpdateRequestDto.builder()
                .name("수정된 작업명세서1")
                .robotId(2L)
                .isRepeat(false)
                .build();
        RobotEntity robotEntity2 = RobotEntity.builder()
                .address("168.192.0.1:22")
                .user("anonymous")
                .password("1234")
                .build();
        given(robotRepository.findById(anyLong())).willReturn(Optional.of(robotEntity2));

        long jobDescriptorId = 1L;
        // When
        Optional<Long> gotJobDescriptorId = jobDescriptorService.update(jobDescriptorId, jobDescriptorUpdateRequestDto);

        // Then
        then(jobDescriptorRepository).should().findById(anyLong());
        then(robotRepository).should().findById(anyLong());

        assertThat(gotJobDescriptorId).isPresent();
        assertThat(gotJobDescriptorId.get()).isEqualTo(jobDescriptorId);
    }

    @Test
    @DisplayName("작업명세서 수정 - 작업명세서가 존재하지 않을 때")
    void update_InvalidJobDescriptor_ReturnEmptyOptional_Test() {
        // Given
        given(jobDescriptorRepository.findById(anyLong())).willReturn(Optional.empty());

        JobDescriptorUpdateRequestDto jobDescriptorUpdateRequestDto = JobDescriptorUpdateRequestDto.builder()
                .name("수정된 작업명세서444")
                .robotId(1L)
                .isRepeat(false)
                .build();
        RobotEntity robotEntity = RobotEntity.builder()
                .address("127.0.0.1:22")
                .user("anonymous")
                .password("1234")
                .build();
        given(robotRepository.findById(anyLong())).willReturn(Optional.of(robotEntity));

        long jobDescriptorId = 444L;

        // When
        Optional<Long> gotJobDescriptorId = jobDescriptorService.update(jobDescriptorId, jobDescriptorUpdateRequestDto);

        // Then
        then(jobDescriptorRepository).should().findById(anyLong());
        then(robotRepository).should().findById(anyLong());

        assertThat(gotJobDescriptorId).isEmpty();
    }

    @Test
    @DisplayName("작업명세서 수정 - 로봇이 존재하지 않을 때")
    void update_InvalidRobot_ReturnEmptyOptional_Test() {
        // Given
        RobotEntity robotEntity1 = RobotEntity.builder()
                .address("127.0.0.1:22")
                .user("anonymous")
                .password("1234")
                .build();
        JobDescriptorEntity jobDescriptorEntity = JobDescriptorEntity.builder()
                .name("작업명세서")
                .isRepeat(false)
                .robotEntity(robotEntity1)
                .build();
        given(jobDescriptorRepository.findById(anyLong())).willReturn(Optional.of(jobDescriptorEntity));

        JobDescriptorUpdateRequestDto jobDescriptorUpdateRequestDto = JobDescriptorUpdateRequestDto.builder()
                .name("수정된 작업명세서1")
                .robotId(444L)
                .isRepeat(false)
                .build();
        given(robotRepository.findById(anyLong())).willReturn(Optional.empty());

        long jobDescriptorId = 1L;

        // When
        Optional<Long> gotJobDescriptorId = jobDescriptorService.update(jobDescriptorId, jobDescriptorUpdateRequestDto);

        // Then
        then(jobDescriptorRepository).should().findById(anyLong());
        then(robotRepository).should().findById(anyLong());

        assertThat(gotJobDescriptorId).isEmpty();
    }

    @Test
    void deleteTest() {
    }

    @Test
    void executeTest() {
    }
}