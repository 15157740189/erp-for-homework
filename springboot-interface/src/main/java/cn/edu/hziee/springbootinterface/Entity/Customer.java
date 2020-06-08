package cn.edu.hziee.springbootinterface.Entity;


import cn.edu.hziee.springbootinterface.Enum.SexEnum;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Customer {
    private Long id;
    private String userName;
    private SexEnum sex=null;
    private String note;
}
