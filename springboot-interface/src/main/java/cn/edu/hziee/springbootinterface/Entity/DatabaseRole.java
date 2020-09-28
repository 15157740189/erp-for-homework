package cn.edu.hziee.springbootinterface.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_role")
public class DatabaseRole extends Model<DatabaseRole> implements Serializable  {
    private static final long serialVersionUID = 2930184058751317003L;
    @TableId(type= IdType.AUTO)
    Integer id;
    String role_name;
    String note;
}
