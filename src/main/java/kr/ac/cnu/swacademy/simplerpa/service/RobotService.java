package kr.ac.cnu.swacademy.simplerpa.service;

import kr.ac.cnu.swacademy.simplerpa.dto.RobotListResponseDto;
import kr.ac.cnu.swacademy.simplerpa.dto.RobotSaveRequestDto;
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
}
