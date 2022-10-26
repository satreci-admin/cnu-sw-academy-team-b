package kr.ac.cnu.swacademy.simplerpa.service;

import com.jcraft.jsch.*;
import kr.ac.cnu.swacademy.simplerpa.dto.LogOutputDto;
import kr.ac.cnu.swacademy.simplerpa.dto.LogStatus;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

@Slf4j
public class SshService {
    private final String ip;
    private final String port;
    private final String username;
    private final String password;
    private final String[] commands;
    private JSch jsch;
    private Session session;
    private Channel channel;
    private StringBuffer output;

    public SshService(String address, String username, String password, String[] commands) {
        this.username = username;
        this.password = password;
        this.ip = address.split(":")[0];
        this.port = address.split(":")[1];
        this.commands = commands;
    }

    public LogOutputDto start() {
        try {
            this.connect();
            this.sendCommands();
            log.info("{}", output);
            return this.parseOutput(output);
        } catch (JSchException | IOException e) {
            return this.parseOutput(e);
        }
    }

    public void connect()  throws JSchException {
        jsch = new JSch();
        session = jsch.getSession(username, ip, Integer.parseInt(port));
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
    }

    public void sendCommands() throws JSchException, IOException {
        channel = session.openChannel("exec");
        ChannelExec channelExec = (ChannelExec) channel;
        StringBuffer joinedCommand = new StringBuffer("");
        for(String command : commands) {
            joinedCommand.append(command);
            joinedCommand.append(";");
        }

        channelExec.setCommand(joinedCommand.toString());
        channelExec.setPty(true);

        output = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(channel.getInputStream()));

        channel.connect();

        // 출력 저장
        String line;
        while((line = br.readLine()) != null) {
            output.append(line);
        }

        channel.disconnect();
    }

    private LogOutputDto parseOutput(StringBuffer output) {
        // error, fault, err,
        StringBuffer outputLog = new StringBuffer();
        String[] errorMessage = new String[] {"error", "fault", "err", "cannot"};
        boolean error = false;

        for(String message : errorMessage) {
            if (output.toString().contains(message)) {
                outputLog.append("[ERROR] : ");
                outputLog.append(output);
                outputLog.append("\n");
                error = true;
            }
        }
        if(error) {
            return LogOutputDto.builder()
                    .logStatus(LogStatus.ERROR)
                    .message(outputLog.toString())
                    .build();
        }

        outputLog.append("[INFO] : Successfully completed!\n");
        outputLog.append(output.toString());
        return LogOutputDto.builder()
                .logStatus(LogStatus.INFO)
                .message(outputLog.toString())
                .build();
    }

    private LogOutputDto parseOutput(Exception e) {
        return LogOutputDto.builder()
                .logStatus(LogStatus.ERROR)
                .message(e.toString())
                .build();
    }
}
