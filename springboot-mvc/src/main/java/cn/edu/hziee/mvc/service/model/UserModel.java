package cn.edu.hziee.mvc.service.model;

public class UserModel {
    private Integer id;
    private String userName;
    private String gender;
    private String password;

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", gender='" + gender + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
