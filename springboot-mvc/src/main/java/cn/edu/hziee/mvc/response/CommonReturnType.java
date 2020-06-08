package cn.edu.hziee.mvc.response;


public class CommonReturnType {
    //要求返回success或者fail
  private String status;
  //若为success返回数据，若为fail，则返回对应的错误码
  private Object data;
 public static CommonReturnType create(Object data){
   return  CommonReturnType.create(data,"success");
 }
    public static CommonReturnType create(Object data,String status){
          CommonReturnType type=new CommonReturnType();
          type.setStatus(status);
          type.setData(data);
          return type;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
