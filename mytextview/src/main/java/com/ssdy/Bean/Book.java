package com.ssdy.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Describe:
 * Created by ys on 2016/9/18 11:58.
 */
@Entity
public class Book{
    @Id
    private Long id;
    @NotNull
    private String book;
    public String getBook() {
        return this.book;
    }
    public void setBook(String book) {
        this.book = book;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 2053347948)
    public Book(Long id, @NotNull String book) {
        this.id = id;
        this.book = book;
    }
    @Generated(hash = 1839243756)
    public Book() {
    }
}
