package com.example.smtp.mail;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.Socket;

/**
 * Created by CRS-P-112 at : 2022-12-27
 *
 * 여기에 SmtpLink 클래스에 대한 설명을 기술해주세요
 *
 * @author CRS-P-112
 * @version 1.0
 * @since 1.0
 */
public class SmtpLink {

    private final BufferedReader bufferedReader;
    private final BufferedWriter bufferedWriter;

    public SmtpLink(Socket socket) throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void connect() throws IOException {
        int statusCode = getStatusCode(bufferedReader);
        if (statusCode != 220) {
            throw new IllegalArgumentException("SMTP 접속 실패하였습니다.");
        }
    }

    private int getStatusCode(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        String statusCode = line.substring(0, 3);
        if (StringUtils.isNumeric(statusCode)) {
            return Integer.parseInt(statusCode);
        }
        return -1;
    }

    private int getStatusCode(String line) throws IOException {
        String statusCode = line.substring(0, 3);
        if (StringUtils.isNumeric(statusCode)) {
            return Integer.parseInt(statusCode);
        }
        return -1;
    }

    public void link(String transferMessage, String errorMessage, int code) throws IOException {
        sendMessage(bufferedWriter, transferMessage);
//        int statusCode = getStatusCode(bufferedReader);
        String line = bufferedReader.readLine();
        int statusCode = getStatusCode(line);
        if (statusCode != code) {
            throw new IllegalArgumentException(errorMessage + " : " + line);
        }
    }

    private void sendMessage(BufferedWriter bufferedWriter, String text) throws IOException {
        bufferedWriter.write(text + "\r\n");
        bufferedWriter.flush();
    }

}