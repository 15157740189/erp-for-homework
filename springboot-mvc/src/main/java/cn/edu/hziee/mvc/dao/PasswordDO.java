package cn.edu.hziee.mvc.dao;

import lombok.Data;

import java.io.Serializable;


public class PasswordDO implements Serializable {
    private static final long serialVersionUID = -2521882932424309677L;
    private Integer id;
    private Integer userId;
    private String password;

    @Override
    public String toString() {
        return "PasswordDO{" +
                "id=" + id +
                ", userId=" + userId +
                ", password='" + password + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
