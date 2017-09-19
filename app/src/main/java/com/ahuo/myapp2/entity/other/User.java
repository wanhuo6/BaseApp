package com.ahuo.myapp2.entity.other;

/**
 * Created on 17-5-27
 *
 * @author liuhuijie
 */

public class User {

    private Integer id;
    private String uuid;
    private String name;
    private Integer age;
    private String photo;
    private Double height;
    private Double weight;
    private String blog;
    private String headimage;
    private String account;
    private String password;
    private Integer sex;

    public User() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null?null:uuid.trim();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name == null?null:name.trim();
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo == null?null:photo.trim();
    }

    public Double getHeight() {
        return this.height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return this.weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getBlog() {
        return this.blog;
    }

    public void setBlog(String blog) {
        this.blog = blog == null?null:blog.trim();
    }

    public String getHeadimage() {
        return this.headimage;
    }

    public void setHeadimage(String headimage) {
        this.headimage = headimage == null?null:headimage.trim();
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account == null?null:account.trim();
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password == null?null:password.trim();
    }

    public Integer getSex() {
        return this.sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
