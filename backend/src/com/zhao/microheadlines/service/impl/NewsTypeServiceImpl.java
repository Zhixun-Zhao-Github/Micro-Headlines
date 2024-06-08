package com.zhao.microheadlines.service.impl;

import com.zhao.microheadlines.dao.NewsTypeDao;
import com.zhao.microheadlines.dao.impl.NewsTypeDaoImpl;
import com.zhao.microheadlines.pojo.NewsType;
import com.zhao.microheadlines.service.NewsTypeService;

import java.util.List;

public class NewsTypeServiceImpl implements NewsTypeService {
    private NewsTypeDao typeDao  = new NewsTypeDaoImpl();
    @Override
    public List<NewsType> findAll() {
        return typeDao.findAll();
    }
}
