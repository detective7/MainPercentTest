package com.example.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.bean.User;
import com.example.bean.information;

import com.example.dao.UserDao;
import com.example.dao.informationDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig userDaoConfig;
    private final DaoConfig informationDaoConfig;

    private final UserDao userDao;
    private final informationDao informationDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        informationDaoConfig = daoConfigMap.get(informationDao.class).clone();
        informationDaoConfig.initIdentityScope(type);

        userDao = new UserDao(userDaoConfig, this);
        informationDao = new informationDao(informationDaoConfig, this);

        registerDao(User.class, userDao);
        registerDao(information.class, informationDao);
    }
    
    public void clear() {
        userDaoConfig.clearIdentityScope();
        informationDaoConfig.clearIdentityScope();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public informationDao getInformationDao() {
        return informationDao;
    }

}
