package com.example.bean;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.
/**
 * Entity mapped to table "user".
 */
@Entity(nameInDb = "user")
public class User implements java.io.Serializable {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String name;
    private Integer age;
    private Double high;

    @Generated(hash = 586692638)
    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    @Generated(hash = 1917775038)
    public User(Long id, @NotNull String name, Integer age, Double high) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.high = high;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(@NotNull String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

}
