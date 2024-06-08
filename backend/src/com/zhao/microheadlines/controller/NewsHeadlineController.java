package com.zhao.microheadlines.controller;

import com.zhao.microheadlines.common.Result;
import com.zhao.microheadlines.pojo.NewsHeadline;
import com.zhao.microheadlines.service.NewsHeadlineService;
import com.zhao.microheadlines.service.impl.NewsHeadlineServiceImpl;
import com.zhao.microheadlines.util.JwtHelper;
import com.zhao.microheadlines.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/headline/*")
public class NewsHeadlineController extends BaseController{
    private NewsHeadlineService newsHeadlineService = new NewsHeadlineServiceImpl();


    protected void removeByHid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int hid = Integer.parseInt(req.getParameter("hid"));
        newsHeadlineService.removeByHid(hid);
        WebUtil.writeJson(resp,Result.ok(null));
    }

    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NewsHeadline newsHeadline = WebUtil.readJson(req, NewsHeadline.class);
        newsHeadlineService.update(newsHeadline);
        WebUtil.writeJson(resp,Result.ok(null));

    }

    protected void findHeadlineByHid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer hid = Integer.parseInt(req.getParameter("hid"));
        NewsHeadline newsHeadline =  newsHeadlineService.findByHid(hid);
        Map data = new HashMap();
        data.put("headline", newsHeadline);
        WebUtil.writeJson(resp,Result.ok(data));
    }

    protected void publish(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getHeader("token");
        Long userId = JwtHelper.getUserId(token);
        NewsHeadline newsHeadline = WebUtil.readJson(req, NewsHeadline.class);
        newsHeadline.setPublisher(userId.intValue());

        newsHeadlineService.addNewsHeadline(newsHeadline);
        WebUtil.writeJson(resp, Result.ok(null));
    }
}
