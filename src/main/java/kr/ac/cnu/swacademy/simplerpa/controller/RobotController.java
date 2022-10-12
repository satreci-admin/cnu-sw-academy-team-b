package kr.ac.cnu.swacademy.simplerpa.controller;

import kr.ac.cnu.swacademy.simplerpa.dto.RobotListResponseDto;
import kr.ac.cnu.swacademy.simplerpa.dto.RobotResponseDto;
import kr.ac.cnu.swacademy.simplerpa.dto.RobotSaveRequestDto;
import kr.ac.cnu.swacademy.simplerpa.dto.RobotUpdateRequestDto;
import kr.ac.cnu.swacademy.simplerpa.entity.RobotEntity;
import kr.ac.cnu.swacademy.simplerpa.service.RobotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RobotController {

    private final RobotService robotService;

    @GetMapping("/robots")
    public String robotList (Model model) {
        List<RobotListResponseDto> robotList = robotService.findAll();
        model.addAttribute("robotList", robotList);
        return "robot/robotList";
    }

    @GetMapping("/robot/{robotId}")
    public String robot (@PathVariable Long robotId, Model model) {
        RobotResponseDto responseDto = robotService.findById(robotId);
        model.addAttribute("robot", responseDto);
        return "robot/detail";
    }

    @GetMapping("/robot")
    public String RobotSaveForm (Model model) {
        model.addAttribute("robot", new RobotSaveRequestDto());
        return "robot/saveForm";
    }

    @PostMapping("/robot")
    public String saveRobot (@ModelAttribute RobotSaveRequestDto requestDto) {
        Long saveId = robotService.save(requestDto);
        return "redirect:/robots";
    }

    @PutMapping("/robot/{robotId}")
    public String updateRobot (
            @PathVariable Long robotId,
            @RequestBody RobotUpdateRequestDto requestDto)
    {
        robotService.update(robotId, requestDto);
        return "robot/robotList";
    }
}
