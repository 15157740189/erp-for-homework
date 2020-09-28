package cn.edu.hziee.springbootinterface.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_user")
public class DatabaseUser extends Model<DatabaseUser> implements Serializable {
    private static final long serialVersionUID = 5654508852460968847L;
    @TableId(type= IdType.AUTO)
    private Integer id;
    @TableField("user_name")
    private String userName;
    private String pwd;
    private Integer available;
    private String note;
}
