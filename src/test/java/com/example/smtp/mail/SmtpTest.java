package com.example.smtp.mail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.naming.NamingException;
import java.io.*;
import java.net.Socket;
import java.util.List;

public class SmtpTest {
    private static final int PORT = 25;

    @DisplayName("메일 주소가 정상적인지 확인한다.")
    @Test
    void construct() throws NamingException, IOException {
        String mail = "yoohee.kim@handok.com";
        Smtp smtp = new Smtp(mail);
        List<String> mailExchangeList = smtp.getMailExchangeList();

        Socket socket = new Socket(mailExchangeList.get(0), PORT);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

//        int statusCode = smtp.getStatusCode(bufferedReader);
//        if (statusCode != 220) {
//            throw new IllegalArgumentException("SMTP 접속 실패하였습니다.");
//        }
//
//        smtp.sendMessage(bufferedWriter, "HELO " + smtp.getDomain());
//        statusCode = smtp.getStatusCode(bufferedReader);
//        if (statusCode != 250) {
//            throw new IllegalArgumentException("ESMTP가 아닙니다.");
//        }
//
//        smtp.sendMessage(bufferedWriter, "MAIL FROM: <sysmail@crscube.co.kr>");
//        statusCode = smtp.getStatusCode(bufferedReader);
//        if (statusCode != 250) {
//            throw new IllegalArgumentException("발송 거부되었습니다.");
//        }
//
//        smtp.sendMessage(bufferedWriter, "RCPT TO: <" + mail + ">");
//        statusCode = smtp.getStatusCode(bufferedReader);
//        if (statusCode != 250) {
//            throw new IllegalArgumentException("발송 거부되었습니다.");
//        }
//
//        smtp.sendMessage(bufferedWriter, "RSET");
//        statusCode = smtp.getStatusCode(bufferedReader);
//        if (statusCode != 250) {
//            throw new IllegalArgumentException("발송 거부되었습니다.");
//        }
//
//        smtp.sendMessage(bufferedWriter, "QUIT");
//        statusCode = smtp.getStatusCode(bufferedReader);
//        if (statusCode != 221) {
//            throw new IllegalArgumentException("발송 거부되었습니다.");
//        }
    }
}