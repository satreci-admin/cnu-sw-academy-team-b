package kr.ac.cnu.swacademy.simplerpa.service;

import kr.ac.cnu.swacademy.simplerpa.dto.RobotListResponseDto;
import kr.ac.cnu.swacademy.simplerpa.dto.RobotResponseDto;
import kr.ac.cnu.swacademy.simplerpa.dto.RobotSaveRequestDto;
import kr.ac.cnu.swacademy.simplerpa.dto.RobotUpdateRequestDto;
import kr.ac.cnu.swacademy.simplerpa.entity.RobotEntity;
import kr.ac.cnu.swacademy.simplerpa.repository.RobotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RobotService {

    private final RobotRepository robotRepository;

    @Transactional
    public Long save(RobotSaveRequestDto requestDto) {
        return robotRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public List<RobotListResponseDto> findAll() {
        return robotRepository
                .findAll()
                .stream()
                .map(RobotListResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public RobotResponseDto findById(Long id) {
        RobotEntity robotEntity = robotRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 id의 로봇이 없습니다"));
        return new RobotResponseDto(robotEntity);
    }

    @Transactional
    public Long update(Long id, RobotUpdateRequestDto requestDto) {
        RobotEntity robotEntity = robotRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 로봇이 없습니다."));
        robotEntity.setAddress(requestDto.getAddress());
        robotEntity.setUser(requestDto.getUser());
        robotEntity.setPassword(requestDto.getPassword());

        return id;
    }

    @Transactional
    public Long delete(Long id) {
        robotRepository.deleteById(id);
        return id;
    }
}
