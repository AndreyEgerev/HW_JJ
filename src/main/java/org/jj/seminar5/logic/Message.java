package org.jj.seminar5.logic;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Message {
    String msg;
    String from;
    String to;
    @JsonFormat(pattern = "dd/MM/yyyy/ H:m:s")
    LocalDateTime dateMsg;

    public Message() {
    }

    public Message(String msg, String from, String to, LocalDateTime dateMsg) {
        this.msg = msg;
        this.from = from;
        this.to = to;
        this.dateMsg = dateMsg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

//    public LocalDate getDateMsg() {
//        return dateMsg;
//    }
//
//    public void setDateMsg(LocalDate dateMsg) {
//        this.dateMsg = dateMsg;
//    }

    @Override
    public String toString() {
        if (to != null) {
            return dateMsg + " " + from + ": @" + to + ", " + msg;
        } else {
            return dateMsg + " " + from + ": " + msg;
        }
    }
}
