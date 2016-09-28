package com.ssdy.Bean;

import com.ssdy.greendao.BookDao;
import com.ssdy.greendao.DaoSession;
import com.ssdy.greendao.UserDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

/**
 * Describe:
 * Created by ys on 2016/9/18 11:59.
 */
@Entity
public class User {
    @Id(autoincrement=true)  //此处为自增长
    private Long user_id;
    @NotNull
    private String Name;
    private int age;
    private int height;
    //官网方法二，表连接
    private String bookName;
    @ToMany(joinProperties = {
            @JoinProperty(name = "bookName",referencedName = "book")
    })
    private List<Book> ownedBooks;
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
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 72181573)
    public synchronized void resetOwnedBooks() {
        ownedBooks = null;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1432356918)
    public List<Book> getOwnedBooks() {
        if (ownedBooks == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            BookDao targetDao = daoSession.getBookDao();
            List<Book> ownedBooksNew = targetDao._queryUser_OwnedBooks(bookName);
            synchronized (this) {
                if(ownedBooks == null) {
                    ownedBooks = ownedBooksNew;
                }
            }
        }
        return ownedBooks;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2059241980)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserDao() : null;
    }
    /** Used for active entity operations. */
    @Generated(hash = 1507654846)
    private transient UserDao myDao;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    public String getBookName() {
        return this.bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public int getHeight() {
        return this.height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getName() {
        return this.Name;
    }
    public void setName(String Name) {
        this.Name = Name;
    }
    public Long getUser_id() {
        return this.user_id;
    }
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
    @Generated(hash = 293634540)
    public User(Long user_id, @NotNull String Name, int age, int height,
            String bookName) {
        this.user_id = user_id;
        this.Name = Name;
        this.age = age;
        this.height = height;
        this.bookName = bookName;
    }
    @Generated(hash = 586692638)
    public User() {
    }
}
