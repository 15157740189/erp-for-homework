package cn.edu.hziee.springbootinterface.Entity;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = -674827235564207701L;
    @NotNull(message = "用户id不可为空")
private Long id;

    @NotNull(message = "用户账号不可为空")
    @Size(min = 6,max = 11,message = "账号长度必须为6-11的字符")
private String account;

    @NotNull(message = "用户密码不能为空")
    @Size(min = 6,max = 11,message = "密码长度必须为6-11的字符")
private String password;

    @NotNull(message = "用户邮箱不能为空")
    @Email(message = "邮箱格式不正确")
private String email;


}
