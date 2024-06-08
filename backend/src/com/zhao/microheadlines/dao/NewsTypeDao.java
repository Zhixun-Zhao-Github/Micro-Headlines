package com.zhao.microheadlines.dao;

import com.zhao.microheadlines.pojo.NewsType;

import java.util.List;

public interface NewsTypeDao {
    List<NewsType> findAll();
}
