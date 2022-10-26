package kr.ac.cnu.swacademy.simplerpa.service;

import kr.ac.cnu.swacademy.simplerpa.dto.LogOutputDto;
import kr.ac.cnu.swacademy.simplerpa.dto.LogStatus;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.*;

class SshServiceTest {
    private Logger log = LoggerFactory.getLogger("logback");

    @Test
    void 원격pc에_접속하고_명령어를_실행하고_info_로그를_반환받는다 () {
        // given
        String[] commands = new String[3];
        commands[0] = "touch hello";
        commands[1] = "echo \"hello world\" >> hello ";
        commands[2] = "cp hello hello_world";
        SshService sshService = new SshService("192.168.64.5:22", "skb", "1234", commands);

        // when
        LogOutputDto outputDto = sshService.start();

        // then
        log.info("{} : {} ", outputDto.getLogStatus().toString(), outputDto.getMessage());
        assertThat(outputDto.getLogStatus().toString()).isEqualTo(LogStatus.INFO.toString());
    }

    @Test
    void 없는_파일을_삭제하는_명령어를_보내_error_로그를_반환받는다 () {
        // given
        String[] commands = new String[1];
        commands[0] = "rm abcd";
        SshService sshService = new SshService("192.168.64.5:22", "skb", "1234", commands);

        // when
        LogOutputDto outputDto = sshService.start();

        // then
        log.info("{} : {} ", outputDto.getLogStatus().toString(), outputDto.getMessage());
        assertThat(outputDto.getLogStatus().toString()).isEqualTo(LogStatus.ERROR.toString());
    }

    @Test
    void 잘못된_로봇_정보를_입력해_error_로그를_반환받는다 () {
        // given
        String[] commands = new String[1];
        commands[0] = "rm abcd";
        SshService sshService = new SshService("333.333.64.5:22", "12", "1234", commands);

        // when
        LogOutputDto outputDto = sshService.start();

        // then
        log.info("{} : {} ", outputDto.getLogStatus().toString(), outputDto.getMessage());
        assertThat(outputDto.getLogStatus().toString()).isEqualTo(LogStatus.ERROR.toString());
    }

}