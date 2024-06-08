package com.zhao.microheadlines.dao;

import com.zhao.microheadlines.pojo.NewsUser;

public interface NewsUserDao {
    /**
     * userdata by username
     * @param username
     * @return
     */
    NewsUser findByUsername(String username);

    /**
     * userdata by userid
     * @param userId
     * @return
     */
    NewsUser findByUid(Integer userId);

    /**
     * insert user to database
     * @param registUser
     * @return
     */
    Integer insertUser(NewsUser registUser);
}
