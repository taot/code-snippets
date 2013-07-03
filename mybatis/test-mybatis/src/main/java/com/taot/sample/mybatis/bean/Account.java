package com.taot.sample.mybatis.bean;

public class Account {

    private Integer id;
    private String owner;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getOwner() {
        return owner;
    }
    
    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Account [id=" + id + ", owner=" + owner + "]";
    }
}
