package com.ssdy.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Describe: 一对多关联，
 * Created by ys on 2016/9/30 16:19.
 */
@Entity
public class Site {

    @Id private Long id;
    //官网上没有notnull，这里我设置为不为空，就是每个场所得有人经过再添加
    @NotNull private Long ownerId;
    public Long getOwnerId() {
        return this.ownerId;
    }
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1314350376)
    public Site(Long id, @NotNull Long ownerId) {
        this.id = id;
        this.ownerId = ownerId;
    }
    @Generated(hash = 1136322986)
    public Site() {
    }
}
