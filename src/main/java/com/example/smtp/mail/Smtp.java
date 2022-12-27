package com.example.smtp.mail;

import org.apache.commons.lang3.StringUtils;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by CRS-P-112 at : 2022-12-27
 *
 * 여기에 Smtp 클래스에 대한 설명을 기술해주세요
 *
 * @author CRS-P-112
 * @version 1.0
 * @since 1.0
 */
public class Smtp {

    private final String email;

    public Smtp(String email) {
        this.email = email;
    }

    public String getDomain() {
        String[] splitMail = email.split("@"); // FIXME s email validation
        if (splitMail.length < 2) {
            throw new IllegalArgumentException("적절한 메일 주소 형식이 아닙니다.");
        }
        return splitMail[1];
    }

    public List<String> getMailExchangeList() throws NamingException {
        Hashtable<Object, Object> environment = new Hashtable<>();
        environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.dns.DnsContextFactory");

        DirContext initialDirContext = new InitialDirContext(environment);
        Attributes attributes = initialDirContext.getAttributes(getDomain(), new String[]{"MX"});
        Attribute mailExchange = attributes.get("MX");
        NamingEnumeration<?> mailExchanges = mailExchange.getAll();

        ArrayList<String> results = new ArrayList<>();
        while (mailExchanges.hasMore()) {
            String nextMx = String.valueOf(mailExchanges.next());
            String[] splitNextMx = nextMx.split(" ");
            if (splitNextMx.length < 2) {
                throw new IllegalArgumentException("유효하지 않은 MX 정보입니다.");
            }

            if (splitNextMx[1].endsWith(".")) {
                results.add(splitNextMx[1].substring(0, (splitNextMx[1].length() - 1)));
                continue;
            }
            results.add(splitNextMx[1]);
        }
        return results;
    }

    public void sendMessage(BufferedWriter bufferedWriter, String text) throws IOException {
        bufferedWriter.write(text + "\r\n");
        bufferedWriter.flush();
    }
}