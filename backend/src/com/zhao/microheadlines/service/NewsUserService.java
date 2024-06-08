package com.zhao.microheadlines.service;

import com.zhao.microheadlines.pojo.NewsUser;

public interface NewsUserService {
    /**
     * Userservice by username
     * @param username
     * @return
     */
    NewsUser findByUsername(String username);

    /**
     * userservice by userid
     * @param userId
     * @return
     */
    NewsUser findByUid(Integer userId);

    /**
     * regist user
     * @param registUser
     * @return
     */
    Integer registUser(NewsUser registUser);
}
