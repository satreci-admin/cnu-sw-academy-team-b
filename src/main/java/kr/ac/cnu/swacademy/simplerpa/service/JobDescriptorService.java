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
import java.util.*;
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
    public Optional<JobDescriptorResponseDto> findById(Long id) {
        Optional<JobDescriptorEntity> jobDescriptorEntity = jobDescriptorRepository.findById(id);
        return jobDescriptorEntity.isEmpty() ? Optional.empty() : Optional.of(new JobDescriptorResponseDto(jobDescriptorEntity.get()));
    }

    @Transactional
    public Optional<Long> save(JobDescriptorSaveRequestDto requestDto) {
        RobotEntity robotEntity = null;
        if(Objects.nonNull(requestDto.getRobotId())) {
            Optional<RobotEntity> gotRobotEntity = robotRepository.findById(requestDto.getRobotId());
            if(gotRobotEntity.isEmpty()) {
                return Optional.empty();
            }
            robotEntity = gotRobotEntity.get();
        }
        return Optional.ofNullable(jobDescriptorRepository.save(requestDto.toEntity(robotEntity)).getId());
    }

    @Transactional
    public Optional<Long> update(Long id, JobDescriptorUpdateRequestDto requestDto) {
        Optional<JobDescriptorEntity> gotJobDescriptorEntity = jobDescriptorRepository.findById(id);
        Optional<RobotEntity> gotRobotEntity = robotRepository.findById(requestDto.getRobotId());

        if(gotJobDescriptorEntity.isEmpty() || gotRobotEntity.isEmpty()) {
            return Optional.empty();
        }

        JobDescriptorEntity jobDescriptorEntity = gotJobDescriptorEntity.get();
        RobotEntity robotEntity = gotRobotEntity.get();

        jobDescriptorEntity.setName(requestDto.getName());
        jobDescriptorEntity.setRobotEntity(robotEntity);
        jobDescriptorEntity.setIsRepeat(requestDto.getIsRepeat());

        String executedDatetime = requestDto.getExecutedDatetime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime executedLocalDatetime = (Objects.isNull(executedDatetime) || Objects.equals(executedDatetime, "")) ? null : LocalDateTime.parse(executedDatetime, formatter);
        jobDescriptorEntity.setExecutedDatetime(executedLocalDatetime);

        return Optional.of(id);
    }

    @Transactional
    public Optional<Long> delete(Long id) {
        Optional<JobDescriptorEntity> jobDescriptorEntity = jobDescriptorRepository.findById(id);
        if(jobDescriptorEntity.isEmpty()) {
            return Optional.empty();
        }
        jobDescriptorRepository.delete(jobDescriptorEntity.get());
        return Optional.of(id);
    }

    @Transactional(readOnly = true)
    public Optional<LogOutputDto> execute(Long id) {
        Optional<JobDescriptorEntity> gotJobDescriptorEntity = jobDescriptorRepository.findById(id);
        if(gotJobDescriptorEntity.isEmpty()) return Optional.empty();
        JobDescriptorEntity jobDescriptorEntity = gotJobDescriptorEntity.get();

        RobotEntity robotEntity = jobDescriptorEntity.getRobotEntity();
        if(Objects.isNull(robotEntity)) return Optional.empty();
        String address = robotEntity.getAddress();
        String username = robotEntity.getUser();
        String password = robotEntity.getPassword();

        List<String> commandList = new ArrayList<>();
        jobDescriptorEntity.getJobEntityList().forEach((jobEntity -> commandList.add(jobEntity.getCommand() + " " + jobEntity.getParameter())));
        int commandListSize = commandList.size();

        return Optional.of(SshService.start(username, address, password, commandList.toArray(new String[commandListSize])));
    }
}
