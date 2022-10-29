package kr.ac.cnu.swacademy.simplerpa.service;

import com.jcraft.jsch.*;
import kr.ac.cnu.swacademy.simplerpa.dto.LogOutputDto;
import kr.ac.cnu.swacademy.simplerpa.dto.LogStatus;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

@Slf4j
@NoArgsConstructor
public class SshService {
    public static LogOutputDto start(String username, String address, String password, String[] commands) {
        try {
            String ip = address.split(":")[0];
            int port = Integer.parseInt(address.split(":")[1]);
            Session session = connect(username, ip, port, password);
            StringBuffer output = exec(session, commands);
            return parseOutput(output);
        } catch (JSchException | IOException e) {
            return parseOutput(e);
        }
    }

    private static Session connect(String username, String ip, int port, String password)  throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, ip, port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        return session;
    }

    private static StringBuffer exec(Session session, String[] commands) throws JSchException, IOException {
        Channel channel = session.openChannel("exec");
        ChannelExec channelExec = (ChannelExec) channel;
        StringBuffer joinedCommand = new StringBuffer("");
        for(String command : commands) {
            joinedCommand.append(command);
            joinedCommand.append(";");
        }

        channelExec.setCommand(joinedCommand.toString());
        channelExec.setPty(true);

        StringBuffer output = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(channel.getInputStream()));

        channel.connect();

        // 출력 저장
        String line;
        while((line = br.readLine()) != null) {
            output.append(line).append("\n");
        }

        channel.disconnect();

        log.info("output : {}", output.toString());
        return output;
    }

    private static LogOutputDto parseOutput(StringBuffer output) {
        // error, fault, err,
        StringBuffer outputLog = new StringBuffer();
        String[] errorMessage = new String[] {"error", "fault", "err", "cannot", "failed"};
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

    private static LogOutputDto parseOutput(Exception e) {
        return LogOutputDto.builder()
                .logStatus(LogStatus.ERROR)
                .message(e.toString())
                .build();
    }
}
