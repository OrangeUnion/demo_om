package com.example.demo.data;

public class SysUser {
    private int id;
    private String tag;
    private String name;
    private String isCp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "id=" + id +
                ", tag='" + tag + '\'' +
                ", name='" + name + '\'' +
                ", isCp='" + isCp + '\'' +
                '}';
    }

    public String getIsCp() {
        return isCp;
    }

    public void setIsCp(String isCp) {
        this.isCp = isCp;
    }
}
