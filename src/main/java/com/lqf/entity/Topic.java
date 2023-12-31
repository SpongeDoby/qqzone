package com.lqf.entity;



import java.time.LocalDateTime;
import java.util.List;


public class Topic {
    private Integer id;
    private String title;
    private String content;
    private LocalDateTime topicDate;
    private UserBasic author;
    private List<Reply> replies;

    public Topic() {
    }

    public Topic(Integer id) {
        this.id=id;
    }


    public Topic(Integer id, String title, String content, LocalDateTime topicDate,UserBasic author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.topicDate = topicDate;
        this.author=author;
    }

    public Topic(String title, String content, LocalDateTime topicDate, UserBasic author) {
        this.title = title;
        this.content = content;
        this.topicDate = topicDate;
        this.author = author;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTopicDate() {
        return topicDate;
    }

    public void setTopicDate(LocalDateTime topicDate) {
        this.topicDate = topicDate;
    }

    public UserBasic getAuthor() {
        return author;
    }

    public void setAuthor(UserBasic author) {
        this.author = author;
    }
}