package cn.edu.hziee.thymeleaf.model;

import java.io.Serializable;


public class Order implements Serializable {
    private static final long serialVersionUID = -4158535950042487415L;
    private String name;
    private String id;
    private String messageId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
