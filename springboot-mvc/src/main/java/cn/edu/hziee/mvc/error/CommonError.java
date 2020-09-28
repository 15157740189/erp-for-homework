package cn.edu.hziee.mvc.error;

public interface CommonError {
     int getErrorCode();
     String getErrorMsg();
     CommonError setErrorMsg(String errorMsg);
}
