package com.zhao.microheadlines.service.impl;

import com.zhao.microheadlines.dao.NewsUserDao;
import com.zhao.microheadlines.dao.impl.NewsUserDaoImpl;
import com.zhao.microheadlines.pojo.NewsUser;
import com.zhao.microheadlines.service.NewsUserService;
import com.zhao.microheadlines.util.MD5Util;

public class NewsUserServiceImpl implements NewsUserService {
    private NewsUserDao newsUserDao = new NewsUserDaoImpl();
    @Override
    public NewsUser findByUsername(String username) {

        return newsUserDao.findByUsername(username);
    }

    @Override
    public NewsUser findByUid(Integer userId) {
        return newsUserDao.findByUid(userId);
    }

    @Override
    public Integer registUser(NewsUser registUser) {
        registUser.setUserPwd(MD5Util.encrypt(registUser.getUserPwd()));
        return newsUserDao.insertUser(registUser);
    }
}
