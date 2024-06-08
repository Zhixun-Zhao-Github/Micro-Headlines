package com.zhao.microheadlines.service.impl;

import com.zhao.microheadlines.dao.NewsHeadLineDao;
import com.zhao.microheadlines.dao.impl.NewsHeadlineDaoImpl;
import com.zhao.microheadlines.pojo.NewsHeadline;
import com.zhao.microheadlines.pojo.vo.HeadlineDetailVo;
import com.zhao.microheadlines.pojo.vo.HeadlinePageVo;
import com.zhao.microheadlines.pojo.vo.HeadlineQueryVo;
import com.zhao.microheadlines.service.NewsHeadlineService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsHeadlineServiceImpl implements NewsHeadlineService {
    private NewsHeadLineDao headLineDao = new NewsHeadlineDaoImpl();
    @Override
    public Map findPage(HeadlineQueryVo headlineQueryVo) {
        int pageNum = headlineQueryVo.getPageNum();
        int pageSize = headlineQueryVo.getPageSize();
        List<HeadlinePageVo> pageData = headLineDao.findPageList(headlineQueryVo);
        int totalSize = headLineDao.findPageCount(headlineQueryVo);
        int totalPage = totalSize % pageSize == 0 ? totalSize / pageSize : totalSize / pageSize + 1;
        Map pageInfo = new HashMap();
        pageInfo.put("pageNum", pageNum);
        pageInfo.put("pageSize", pageSize);
        pageInfo.put("totalSize", totalSize);
        pageInfo.put("totalPage", totalPage);
        pageInfo.put("pageData", pageData);
        return pageInfo;
    }

    @Override
    public HeadlineDetailVo findHeadlineDetail(int hid) {
        headLineDao.incrPageViews(hid);


        return headLineDao.findHeadlineDetail(hid);
    }

    @Override
    public int addNewsHeadline(NewsHeadline newsHeadline) {

        return headLineDao.addNewsHeadline(newsHeadline);
    }

    @Override
    public NewsHeadline findByHid(Integer hid) {
        return headLineDao.findByHid(hid);
    }

    @Override
    public int update(NewsHeadline newsHeadline) {

        return headLineDao.update(newsHeadline);
    }

    @Override
    public int removeByHid(int hid) {

        return headLineDao.removeByHid(hid);
    }
}
