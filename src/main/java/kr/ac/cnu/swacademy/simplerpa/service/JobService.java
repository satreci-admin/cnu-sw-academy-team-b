package kr.ac.cnu.swacademy.simplerpa.service;

import kr.ac.cnu.swacademy.simplerpa.dto.JobListResponseDto;
import kr.ac.cnu.swacademy.simplerpa.dto.JobSaveRequestDto;
import kr.ac.cnu.swacademy.simplerpa.dto.JobUpdateRequestDto;
import kr.ac.cnu.swacademy.simplerpa.entity.JobDescriptorEntity;
import kr.ac.cnu.swacademy.simplerpa.entity.JobEntity;
import kr.ac.cnu.swacademy.simplerpa.repository.JobDescriptorRepository;
import kr.ac.cnu.swacademy.simplerpa.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JobService {

    private final JobRepository jobRepository;
    private final JobDescriptorRepository jobDescriptorRepository;

    @Transactional
    public Optional<JobListResponseDto> findById(Long id)
    {
        JobEntity jobEntity = jobRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 작업이 없습니다. id=" + id));
        return Optional.of(new JobListResponseDto(jobEntity));
    }

    @Transactional
    public List<JobListResponseDto> findByJobDescriptorEntity(Long jobDescriptorId)
    {
        return jobRepository
                .findByJobDescriptorEntity(jobDescriptorId)
                .stream()
                .map(JobListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long create(JobSaveRequestDto jobSaveRequestDto)
    {
        JobDescriptorEntity foundedJobDescriptorEntity = jobDescriptorRepository.findById(jobSaveRequestDto.getJobDescriptionId()).orElseThrow(() ->
                new IllegalArgumentException("해당 작업명세서가 없습니다. id=" + jobSaveRequestDto.getJobDescriptionId()));
        JobEntity savedJobEntity = jobRepository.save(jobSaveRequestDto.toEntity(foundedJobDescriptorEntity));
        return savedJobEntity.getId();
    }

    @Transactional
    public Long update(JobUpdateRequestDto jobUpdateRequestDto, Long jobId)
    {

        JobEntity foundedJobEntity = jobRepository.findById(jobId).orElseThrow(() ->
                new IllegalArgumentException("해당 작업이 없습니다. id=" + jobId));

        foundedJobEntity.setCommand(jobUpdateRequestDto.getCommand());
        foundedJobEntity.setActivation(jobUpdateRequestDto.getActivation());
        foundedJobEntity.setParameter(jobUpdateRequestDto.getParameter());


        return foundedJobEntity.getId();
    }

    @Transactional
    public void delete(Long jobId)
    {
        JobEntity foundedJobEntity = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + jobId));

        jobRepository.delete(foundedJobEntity);
    }


}
