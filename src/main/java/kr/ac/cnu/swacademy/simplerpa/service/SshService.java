package kr.ac.cnu.swacademy.simplerpa.service;

import com.jcraft.jsch.*;
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

    public void start() throws JSchException, IOException{
        this.connect();
        this.sendCommands();
        log.info("{}", output);
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

        // output
        byte[] buffer = new byte[1024];
        // 출력 저장
        String line;
        while((line = br.readLine()) != null) {
            output.append(line);
        }

        channel.disconnect();
    }
}
