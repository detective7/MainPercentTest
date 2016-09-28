package com.ssdy.Bean;

import com.ssdy.greendao.BookDao;
import com.ssdy.greendao.DaoSession;
import com.ssdy.greendao.UserDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;

/**
 * Describe:
 * Created by ys on 2016/9/18 11:58.
 */
@Entity
public class Book{
    @Id(autoincrement=true)  //此处为自增长
    private Long id;
    @NotNull
    private String book;
    private Long userId;
    @ToOne(joinProperty = "userId")
    private User user;
    @Generated(hash = 251390918)
    private transient Long user__resolvedKey;
    /** Used for active entity operations. */
    @Generated(hash = 1097957864)
    private transient BookDao myDao;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

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
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 34413221)
    public void setUser(User user) {
        synchronized (this) {
            this.user = user;
            userId = user == null ? null : user.getUser_id();
            user__resolvedKey = userId;
        }
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 859885876)
    public User getUser() {
        Long __key = this.userId;
        if (user__resolvedKey == null || !user__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            User userNew = targetDao.load(__key);
            synchronized (this) {
                user = userNew;
                user__resolvedKey = __key;
            }
        }
        return user;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1115456930)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getBookDao() : null;
    }
    public Long getUserId() {
        return this.userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    @Generated(hash = 822560630)
    public Book(Long id, @NotNull String book, Long userId) {
        this.id = id;
        this.book = book;
        this.userId = userId;
    }
    @Generated(hash = 1839243756)
    public Book() {
    }
}
