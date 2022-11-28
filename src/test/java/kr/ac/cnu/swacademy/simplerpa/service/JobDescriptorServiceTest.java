package kr.ac.cnu.swacademy.simplerpa.service;

import kr.ac.cnu.swacademy.simplerpa.dto.JobDescriptorResponseDto;
import kr.ac.cnu.swacademy.simplerpa.entity.JobDescriptorEntity;
import kr.ac.cnu.swacademy.simplerpa.repository.JobDescriptorRepository;
import kr.ac.cnu.swacademy.simplerpa.repository.RobotRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void findAllDescTest() {

    }

    @Test
    void findAllByExecutedDatetimeBetweenTest() {
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
    void saveTest() {
    }

    @Test
    void updateTest() {
    }

    @Test
    void deleteTest() {
    }

    @Test
    void executeTest() {
    }
}