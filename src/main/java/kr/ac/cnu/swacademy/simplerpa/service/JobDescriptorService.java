package kr.ac.cnu.swacademy.simplerpa.service;

import kr.ac.cnu.swacademy.simplerpa.dto.*;
import kr.ac.cnu.swacademy.simplerpa.entity.JobDescriptorEntity;
import kr.ac.cnu.swacademy.simplerpa.entity.RobotEntity;
import kr.ac.cnu.swacademy.simplerpa.repository.JobDescriptorRepository;
import kr.ac.cnu.swacademy.simplerpa.repository.RobotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
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
    public List<JobDescriptorResponseDto> findAllByExecutedDatetimeBetween(LocalDateTime startExecutedDatetime, LocalDateTime endExecutedDatetime) {
        return jobDescriptorRepository.findAllByExecutedDatetimeBetween(startExecutedDatetime, endExecutedDatetime).stream()
                .map(JobDescriptorResponseDto::new)
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
        RobotEntity robotEntity;
        if(Objects.isNull(requestDto.getRobotId())) {
            robotEntity = null;
        }else {
            robotEntity = robotRepository
                    .findById(requestDto.getRobotId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 로봇입니다."));
        }
        return jobDescriptorRepository.save(requestDto.toEntity(robotEntity)).getId();
    }

    @Transactional
    public Long update(Long id, JobDescriptorUpdateRequestDto requestDto) {
        JobDescriptorEntity jobDescriptorEntity = jobDescriptorRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 작업명세서가 존재하지않습니다. id=" + id));
        RobotEntity robotEntity = robotRepository
                .findById(requestDto.getRobotId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 로봇입니다."));

        String executedDatetime = requestDto.getExecutedDatetime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime executedLocalDatetime = (Objects.isNull(executedDatetime) || Objects.equals(executedDatetime, "")) ? null : LocalDateTime.parse(executedDatetime, formatter);

        jobDescriptorEntity.setName(requestDto.getName());
        jobDescriptorEntity.setExecutedDatetime(executedLocalDatetime);
        jobDescriptorEntity.setRobotEntity(robotEntity);
        jobDescriptorEntity.setIsRepeat(requestDto.getIsRepeat());
        return id;
    }

    @Transactional
    public Long delete(Long id) {
        JobDescriptorEntity jobDescriptorEntity = jobDescriptorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 작업명세서가 존재하지않습니다. id=" + id));
        jobDescriptorRepository.delete(jobDescriptorEntity);
        return id;
    }

    @Transactional(readOnly = true)
    public LogOutputDto execute(Long id) {
        JobDescriptorEntity jobDescriptorEntity = jobDescriptorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 작업명세서가 존재하지않습니다. id=" + id));

        String address = jobDescriptorEntity.getRobotEntity().getAddress();
        String username = jobDescriptorEntity.getRobotEntity().getUser();
        String password = jobDescriptorEntity.getRobotEntity().getPassword();

        List<String> commandList = new ArrayList<>();
        jobDescriptorEntity.getJobEntityList().forEach((jobEntity -> commandList.add(jobEntity.getCommand() + " " + jobEntity.getParameter())));
        int commandListSize = commandList.size();

        return SshService.start(username, address, password, commandList.toArray(new String[commandListSize]));
    }
}
