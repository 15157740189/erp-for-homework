package cn.edu.hziee.springbootinterface.Entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Data
@Document(collection = "user")
public class Cup implements Serializable {
    private static final long serialVersionUID = 4119392949899590904L;
   @Id
    private String Id;
    //@Field("cup_id")
    private Long cup_id;
    //@Field("cup_name")
    private String cup_name;
    private String note;
    private List<Role> roles;
}
