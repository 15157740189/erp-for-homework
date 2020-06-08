package cn.edu.hziee.mvc.dao;

import java.io.Serializable;
import java.util.Set;

public class UserDO implements Serializable {
    private static final long serialVersionUID = -4353346993030521390L;
    private Integer id;
    private String userName;
    private String gender;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "UserDO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}