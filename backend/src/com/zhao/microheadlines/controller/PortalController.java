package com.zhao.microheadlines.controller;

import com.zhao.microheadlines.common.Result;
import com.zhao.microheadlines.pojo.NewsType;
import com.zhao.microheadlines.pojo.vo.HeadlineDetailVo;
import com.zhao.microheadlines.pojo.vo.HeadlineQueryVo;
import com.zhao.microheadlines.service.NewsHeadlineService;
import com.zhao.microheadlines.service.NewsTypeService;
import com.zhao.microheadlines.service.impl.NewsHeadlineServiceImpl;
import com.zhao.microheadlines.service.impl.NewsTypeServiceImpl;
import com.zhao.microheadlines.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/portal/*")
public class PortalController extends BaseController{
    private NewsTypeService typeService = new NewsTypeServiceImpl();
    private NewsHeadlineService headlineService = new NewsHeadlineServiceImpl();

    /**
     * find headline detail
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void showHeadlineDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int hid = Integer.parseInt(req.getParameter("hid"));
        HeadlineDetailVo headlineDetailVo = headlineService.findHeadlineDetail(hid);
        Map data = new HashMap();
        data.put("headline", headlineDetailVo);
        WebUtil.writeJson(resp,Result.ok(data));
    }

    /**
     * find Newspage
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void findNewsPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HeadlineQueryVo headlineQueryVo = WebUtil.readJson(req, HeadlineQueryVo.class);
        Map pageInfo = headlineService.findPage(headlineQueryVo);
        Map data = new HashMap();
        data.put("pageInfo", pageInfo);
        WebUtil.writeJson(resp,Result.ok(data));
    }

    protected void findAllTypes(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<NewsType> newsTypeList = typeService.findAll();
        WebUtil.writeJson(resp,Result.ok(newsTypeList));

    }
}
