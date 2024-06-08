package com.zhao.microheadlines.dao.impl;

import com.zhao.microheadlines.dao.BaseDao;
import com.zhao.microheadlines.dao.NewsTypeDao;
import com.zhao.microheadlines.pojo.NewsType;

import java.util.List;

public class NewsTypeDaoImpl extends BaseDao implements NewsTypeDao {
    @Override
    public List<NewsType> findAll() {
        String sql = "select tid,tname from news_type";
        return baseQuery(NewsType.class,sql);
    }
}
