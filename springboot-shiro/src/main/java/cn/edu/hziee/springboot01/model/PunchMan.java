package cn.edu.hziee.springboot01.model;

import javax.validation.constraints.Min;

public class PunchMan {
    @Min(value = 200,message = "拉力不足200kg的英雄不允许存在")
    Integer strength;
    String name;
    String level;

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
