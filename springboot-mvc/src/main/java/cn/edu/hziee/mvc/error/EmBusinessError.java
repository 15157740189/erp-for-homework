package cn.edu.hziee.mvc.error;

public enum EmBusinessError implements CommonError {
    //通用错误类型00001
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    UNKNOW_ERROR(10002,"未知错误"),
    //10000开头为用户信息不存在相关错误
    USER_NOT_EXIST(20001,"用户不存在"),
    IO_FAIL(30001,"IO错误，上传文件失败");
    EmBusinessError(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
    private int errorCode;
    private String errorMsg;

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMsg() {
        return this.errorMsg;
    }

    @Override
    public CommonError setErrorMsg(String errorMsg) {
        this.errorMsg=errorMsg;
        return this;
    }
}
