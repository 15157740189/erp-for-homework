package cn.edu.hziee.mvc.error;
//包装器业务异常类实现
public class BusinessException extends Exception implements CommonError {
    private static final long serialVersionUID = -7228386959968576586L;
    private CommonError commonError;
    //直接接受EmBusinessError构造业务异常
    public BusinessException(CommonError commonError){
        super();
        this.commonError=commonError;
    }
    //接受自定义errorMsg构造业务异常
    public BusinessException(CommonError commonError,String errorMsg){
        super();
        this.commonError=commonError;
        this.commonError.setErrorMsg(errorMsg);
    }
    @Override
    public int getErrorCode() {
        return this.commonError.getErrorCode();
    }

    @Override
    public String getErrorMsg() {
        return this.commonError.getErrorMsg();
    }

    @Override
    public CommonError setErrorMsg(String errorMsg) {
        this.commonError.setErrorMsg(errorMsg);
        return this;
    }
}
