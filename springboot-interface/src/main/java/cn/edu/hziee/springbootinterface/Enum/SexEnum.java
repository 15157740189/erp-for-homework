package cn.edu.hziee.springbootinterface.Enum;

public enum SexEnum {
    MALE(1,"男"),
    FEMALE(2,"女");
    private int sexCode;
    private String sexName;

    SexEnum(int sexCode, String sexName) {
        this.sexCode=sexCode;
        this.sexName=sexName;
    }
    public SexEnum getSexEnum(int sexCode){
        if (sexCode==1) {
            return MALE;
        }else {
            return FEMALE;
        }
    }
}
