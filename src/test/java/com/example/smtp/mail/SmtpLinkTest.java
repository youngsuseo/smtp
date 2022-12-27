package com.example.smtp.mail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.naming.NamingException;
import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * Created by CRS-P-112 at : 2022-12-27
 *
 * 여기에 SmtpLinkTest 클래스에 대한 설명을 기술해주세요
 *
 * @author CRS-P-112
 * @version 1.0
 * @since 1.0
 */
public class SmtpLinkTest {
    private static final int PORT = 487;

    @DisplayName("메일주소가 정상인지 확인한다.")
    @ParameterizedTest
//    @ValueSource(strings = {"adm_pv@admkorea.co.kr", "alerbue@korea.kr", "5happy@hanmi.co.kr", "01092311787k@gmail.com", "01069@snu.ac.kr"})
    @ValueSource(strings = {"testId@gmail.com"})
    void construct() throws NamingException, IOException {
        String mail = "yoohee.kim@handok.com";
        Smtp smtp = new Smtp(mail);
        List<String> mailExchangeList = smtp.getMailExchangeList();

        SmtpLink smtpLink = new SmtpLink(new Socket(mailExchangeList.get(0), PORT));
        smtpLink.connect();
        smtpLink.link("HELO " + smtp.getDomain(), "ESMTP가 아닙니다.", 250);
        smtpLink.link("MAIL FROM: <sysmail@crscube.co.kr>", "올바르지 않은 발신자 입니다.", 250);
        smtpLink.link("RCPT TO: <" + mail + ">", "올바르지 않은 수신자 입니다.", 250);
        smtpLink.link("RSET", "전송 리셋에 실패하였습니다.", 250);
        smtpLink.link("QUIT", "전송 종료에 실패하였습니다.", 221);
    }
}