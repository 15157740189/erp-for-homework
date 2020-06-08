package cn.edu.hziee.springbootinterface.Entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@Document
public class Role implements Serializable {
    private static final long serialVersionUID = -6817448536537790606L;
    @Id
    private String id;

    //@Field("role_id")
    private Long role_id;

    //@Field("role_name")
    private String role_name;

    private String note;

}
