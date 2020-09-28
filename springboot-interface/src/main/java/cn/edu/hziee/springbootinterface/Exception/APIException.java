package cn.edu.hziee.springbootinterface.Exception;

import lombok.Getter;

@Getter
public class APIException extends RuntimeException {
    private static final long serialVersionUID = -8559927061116565199L;
    private int code;
    private String msg;

    public APIException() {
        this(1001, "接口错误");
    }

    public APIException(String msg) {
        this(1001, msg);
    }

    public APIException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

}
