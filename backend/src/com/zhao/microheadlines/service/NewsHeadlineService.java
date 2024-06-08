package com.zhao.microheadlines.service;

import com.zhao.microheadlines.pojo.NewsHeadline;
import com.zhao.microheadlines.pojo.vo.HeadlineDetailVo;
import com.zhao.microheadlines.pojo.vo.HeadlineQueryVo;

import java.util.Map;

public interface NewsHeadlineService {
    /**
     *
     * @param headlineQueryVo
     * @return
     */
    Map findPage(HeadlineQueryVo headlineQueryVo);

    /**
     * find headline detail
     * @param hid
     * @return
     */
    HeadlineDetailVo findHeadlineDetail(int hid);

    /**
     * add headline
     * @param newsHeadline
     */
    int addNewsHeadline(NewsHeadline newsHeadline);

    /**
     * find headline by id
     * @param hid
     * @return
     */
    NewsHeadline findByHid(Integer hid);

    /**
     * update neadline
     * @param newsHeadline
     */
    int update(NewsHeadline newsHeadline);

    /**
     * remove headline by hid
     * @param hid
     */
    int removeByHid(int hid);
}
