package kr.ac.cnu.swacademy.simplerpa.controller.api;

import kr.ac.cnu.swacademy.simplerpa.dto.RobotListResponseDto;
import kr.ac.cnu.swacademy.simplerpa.dto.RobotResponseDto;
import kr.ac.cnu.swacademy.simplerpa.dto.RobotSaveRequestDto;
import kr.ac.cnu.swacademy.simplerpa.dto.RobotUpdateRequestDto;
import kr.ac.cnu.swacademy.simplerpa.service.RobotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RobotApiController {

    private final RobotService robotService;

    @GetMapping("/api/v1/robots")
    public ResponseEntity<List<RobotListResponseDto>> robotList() {
        List<RobotListResponseDto> all = robotService.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/api/v1/robot/{robotId}")
    public ResponseEntity<RobotResponseDto> robot(@PathVariable Long robotId) {
        RobotResponseDto responseDto = robotService.findById(robotId);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/api/v1/robot")
    public ResponseEntity<Long> save(@RequestBody RobotSaveRequestDto requestDto) {
        Long savedId = robotService.save(requestDto);
        return ResponseEntity.ok(savedId);
    }

    @PutMapping("/api/v1/robot/{robotId}")
    public ResponseEntity<Long> update(@PathVariable Long robotId, @RequestBody RobotUpdateRequestDto requestDto) {
        Long updatedId = robotService.update(robotId, requestDto);
        return ResponseEntity.ok(updatedId);
    }

    @DeleteMapping("/api/v1/robot/{robotId}")
    public ResponseEntity<Long> delete(@PathVariable Long robotId) {
        robotService.delete(robotId);
        return ResponseEntity.ok(robotId);
    }
}
