package kr.ac.cnu.swacademy.simplerpa.service;

import kr.ac.cnu.swacademy.simplerpa.dto.JobDescriptorListResponseDto;
import kr.ac.cnu.swacademy.simplerpa.dto.JobDescriptorResponseDto;
import kr.ac.cnu.swacademy.simplerpa.dto.JobDescriptorSaveRequestDto;
import kr.ac.cnu.swacademy.simplerpa.dto.JobDescriptorUpdateRequestDto;
import kr.ac.cnu.swacademy.simplerpa.entity.JobDescriptorEntity;
import kr.ac.cnu.swacademy.simplerpa.entity.RobotEntity;
import kr.ac.cnu.swacademy.simplerpa.repository.JobDescriptorRepository;
import kr.ac.cnu.swacademy.simplerpa.repository.RobotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JobDescriptorService {

    private final JobDescriptorRepository jobDescriptorRepository;
    private final RobotRepository robotRepository;

    @Transactional(readOnly = true)
    public List<JobDescriptorListResponseDto> findAllDesc() {
        return jobDescriptorRepository.findAllDesc().stream()
                .map(JobDescriptorListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public JobDescriptorResponseDto findById(Long id) {
        JobDescriptorEntity jobDescriptorEntity = jobDescriptorRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 작업명세서가 존재하지않습니다. id=" + id));
        return new JobDescriptorResponseDto(jobDescriptorEntity);
    }

    @Transactional
    public Long save(JobDescriptorSaveRequestDto requestDto) {
        // 로봇과 매핑하고 난 뒤, 사용
//        RobotEntity robotEntity = robotRepository
//                .findById(requestDto.getRobotId())
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 로봇입니다."));
        // 로봇과 매핑하기 전, 임시로 사용
        RobotEntity robotEntity = RobotEntity.builder()
                .address("192.168.1.1")
                .user("anonymous")
                .password("1234")
                .build();
        robotRepository.save(robotEntity);
        return jobDescriptorRepository.save(requestDto.toEntity(robotEntity)).getId();
    }

    @Transactional
    public Long update(Long id, JobDescriptorUpdateRequestDto requestDto) {
        JobDescriptorEntity jobDescriptorEntity = jobDescriptorRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 작업명세서가 존재하지않습니다. id=" + id));

        String executedDatetime = requestDto.getExecutedDatetime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime executedLocalDatetime = (Objects.isNull(executedDatetime) || Objects.equals(executedDatetime, "")) ? null : LocalDateTime.parse(executedDatetime, formatter);

        jobDescriptorEntity.setName(requestDto.getName());
        jobDescriptorEntity.setExecutedDatetime(executedLocalDatetime);
        jobDescriptorEntity.setIsRepeat(requestDto.getIsRepeat());
        jobDescriptorEntity.setUpdateAt(LocalDateTime.now());
        return id;
    }

    @Transactional
    public Long delete(Long id) {
        JobDescriptorEntity jobDescriptorEntity = jobDescriptorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 작업명세서가 존재하지않습니다. id=" + id));
        jobDescriptorRepository.delete(jobDescriptorEntity);
        return id;
    }
}