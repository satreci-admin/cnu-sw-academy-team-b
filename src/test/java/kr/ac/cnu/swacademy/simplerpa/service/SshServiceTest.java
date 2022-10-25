package kr.ac.cnu.swacademy.simplerpa.service;

import com.jcraft.jsch.JSchException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SshServiceTest {

    private SshService sshService;

    @Test
    void 원격pc에_접속하고_명령어를_실행한다 () {
        String[] commands = new String[3];
        commands[0] = "touch hello";
        commands[1] = "echo \"hello world\" >> hello ";
        commands[2] = "cp hello hello_world";
        sshService = new SshService("192.168.64.5:22", "skb", "1234", commands);
        try {
            sshService.start();
        }
        catch (JSchException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}