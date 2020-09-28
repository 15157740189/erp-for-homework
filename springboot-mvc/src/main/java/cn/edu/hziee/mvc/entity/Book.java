package cn.edu.hziee.mvc.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)//取消lombok警告
@TableName("book")
public class Book extends Model<Book> implements Serializable {
 private static final long serialVersionUID = -621910806969477750L;
 @TableId(type= IdType.AUTO)
 private Integer id;
 @TableField("bid")
 private String bookId;
 private String type;
 @TableField("bnumber")
 private Integer bookNumber;//数量
 @TableField(value = "bplace",condition = SqlCondition.LIKE)
 private String bookPlace;//位置
 @TableField("cid")
 private String adminId;
 @TableField("pname")
 private String producerName;//生产商
 @TableField("bname")
 private String bookName;//书名
 @TableField("pdate")
 private String produceDate;
 @TableField(exist = false)
 private Integer price=0;
 private transient String remark="暂无备注";//备注，表外的字段
 private static String codingMan;
}
