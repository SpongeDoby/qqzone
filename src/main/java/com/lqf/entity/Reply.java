package com.lqf.entity;

import java.time.LocalDateTime;

/**
 * @author Alitar
 * @date 2023-02-08 16:37
 */
public class Reply {
    private  Integer id;
    private  String content;
    private LocalDateTime replyDate;
    private UserBasic author;
    private Topic topic;
    private HostReply hostReply;

    public Reply() {
    }

    public Reply(UserBasic author) {
        this.author = author;
    }

    public Reply(Integer id,String content, LocalDateTime replyDate, UserBasic author, Topic topic) {
        this.id=id;
        this.content = content;
        this.replyDate = replyDate;
        this.author = author;
        this.topic = topic;
    }

    public Reply(String content, LocalDateTime replyDate, UserBasic author, Topic topic) {
        this.content = content;
        this.replyDate = replyDate;
        this.author = author;
        this.topic = topic;
    }

    public Reply(Integer id) {
        this.id = id;
    }

    public HostReply getHostReply() {
        return hostReply;
    }

    public void setHostReply(HostReply hostReply) {
        this.hostReply = hostReply;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(LocalDateTime replyDate) {
        this.replyDate = replyDate;
    }

    public UserBasic getAuthor() {
        return author;
    }

    public void setAuthor(UserBasic author) {
        this.author = author;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    }