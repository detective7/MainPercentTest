package com.ssdy.Bean;

import com.ssdy.greendao.BookDao;
import com.ssdy.greendao.DaoSession;
import com.ssdy.greendao.SiteDao;
import com.ssdy.greendao.UserDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.List;

/**
 * Describe:
 * Created by ys on 2016/9/18 11:59.
 */
@Entity
public class User {
    @Id(autoincrement=true)  //此处为自增长
    private Long u_id;
    @NotNull
    private String Name;
    private int age;
    private int height;

    private Long bookId;
    @ToOne(joinProperty = "bookId")
    private Book book;

    @ToMany(referencedJoinProperty = "ownerId")
    private List<Site> ownedSites;
//
//    @ToMany(joinProperties = {
//            @JoinProperty(name = "bookName",referencedName = "book")
//    })
//    private List<Book> ownedBooks;

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
    @Generated(hash = 2095874465)
    public synchronized void resetOwnedSites() {
        ownedSites = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 142334661)
    public List<Site> getOwnedSites() {
        if (ownedSites == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SiteDao targetDao = daoSession.getSiteDao();
            List<Site> ownedSitesNew = targetDao._queryUser_OwnedSites(u_id);
            synchronized (this) {
                if(ownedSites == null) {
                    ownedSites = ownedSitesNew;
                }
            }
        }
        return ownedSites;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1118843534)
    public void setBook(Book book) {
        synchronized (this) {
            this.book = book;
            bookId = book == null ? null : book.getB_id();
            book__resolvedKey = bookId;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 577500745)
    public Book getBook() {
        Long __key = this.bookId;
        if (book__resolvedKey == null || !book__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            BookDao targetDao = daoSession.getBookDao();
            Book bookNew = targetDao.load(__key);
            synchronized (this) {
                book = bookNew;
                book__resolvedKey = __key;
            }
        }
        return book;
    }

    @Generated(hash = 893611298)
    private transient Long book__resolvedKey;

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

    public Long getBookId() {
        return this.bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
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

    public Long getU_id() {
        return this.u_id;
    }

    public void setU_id(Long u_id) {
        this.u_id = u_id;
    }

    @Generated(hash = 2031286636)
    public User(Long u_id, @NotNull String Name, int age, int height, Long bookId) {
        this.u_id = u_id;
        this.Name = Name;
        this.age = age;
        this.height = height;
        this.bookId = bookId;
    }

    @Generated(hash = 586692638)
    public User() {
    }

}
