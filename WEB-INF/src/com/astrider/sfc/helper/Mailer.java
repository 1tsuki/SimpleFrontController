package com.astrider.sfc.helper;

import java.util.HashMap;

public class Mailer {
    private HashMap<String, String> from       = new HashMap<String, String>();
    private HashMap<String, String> to         = new HashMap<String, String>();
    private HashMap<String, String> cc         = new HashMap<String, String>();
    private HashMap<String, String> bcc        = new HashMap<String, String>();
    private String                  subject;
    private String                  body;

    public Mailer() {
    }

    public boolean send() {
        return true;
    }

    public HashMap<String, String> getFrom() {
        return from;
    }

    public void setFrom(String name, String email) {
        this.from.put(name, email);
    }

    public void setFrom(HashMap<String, String> from) {
        this.from = from;
    }

    public HashMap<String, String> getTo() {
        return to;
    }

    public void setTo(String name, String email) {
        this.to.put(name, email);
    }

    public void setTo(HashMap<String, String> to) {
        this.to = to;
    }

    public HashMap<String, String> getCc() {
        return cc;
    }

    public void addCc(String name, String email) {
        this.cc.put(name, email);
    }

    public void setCc(HashMap<String, String> cc) {
        this.cc = cc;
    }

    public HashMap<String, String> getBcc() {
        return bcc;
    }

    public void addBcc(String name, String email) {
        this.bcc.put(name, email);
    }

    public void setBcc(HashMap<String, String> bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
