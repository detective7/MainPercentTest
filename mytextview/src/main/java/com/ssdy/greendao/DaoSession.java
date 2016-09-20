package com.ssdy.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.ssdy.Bean.Book;
import com.ssdy.Bean.User;

import com.ssdy.greendao.BookDao;
import com.ssdy.greendao.UserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig bookDaoConfig;
    private final DaoConfig userDaoConfig;

    private final BookDao bookDao;
    private final UserDao userDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        bookDaoConfig = daoConfigMap.get(BookDao.class).clone();
        bookDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        bookDao = new BookDao(bookDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);

        registerDao(Book.class, bookDao);
        registerDao(User.class, userDao);
    }
    
    public void clear() {
        bookDaoConfig.getIdentityScope().clear();
        userDaoConfig.getIdentityScope().clear();
    }

    public BookDao getBookDao() {
        return bookDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

}
