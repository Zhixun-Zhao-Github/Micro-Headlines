package com.zhao.microheadlines.dao;

import com.zhao.microheadlines.pojo.NewsHeadline;
import com.zhao.microheadlines.pojo.vo.HeadlineDetailVo;
import com.zhao.microheadlines.pojo.vo.HeadlinePageVo;
import com.zhao.microheadlines.pojo.vo.HeadlineQueryVo;

import java.util.List;

public interface NewsHeadLineDao {
    /**
     * find page list
     * @param headlineQueryVo
     * @return
     */
    List<HeadlinePageVo> findPageList(HeadlineQueryVo headlineQueryVo);

    /**
     * count the page numbers
     * @param headlineQueryVo
     * @return
     */
    int findPageCount(HeadlineQueryVo headlineQueryVo);

    /**
     * increase page views
     * @param hid
     */
    int incrPageViews(int hid);

    /**
     * find headline detail
     * @param hid
     * @return
     */
    HeadlineDetailVo findHeadlineDetail(int hid);

    /**
     * add headline
     * @param newsHeadline
     * @return
     */
    int addNewsHeadline(NewsHeadline newsHeadline);

    /**
     * find head line by hid
     * @param hid
     * @return
     */
    NewsHeadline findByHid(Integer hid);

    /**
     * update headline
     * @param newsHeadline
     * @return
     */
    int update(NewsHeadline newsHeadline);

    /**
     * remove headline by hid
     * @param hid
     * @return
     */
    int removeByHid(int hid);
}
