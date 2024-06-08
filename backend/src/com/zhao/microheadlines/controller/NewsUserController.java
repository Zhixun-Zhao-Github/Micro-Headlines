package com.zhao.microheadlines.controller;

import com.zhao.microheadlines.common.Result;
import com.zhao.microheadlines.common.ResultCodeEnum;
import com.zhao.microheadlines.pojo.NewsUser;
import com.zhao.microheadlines.service.NewsUserService;
import com.zhao.microheadlines.service.impl.NewsUserServiceImpl;
import com.zhao.microheadlines.util.JwtHelper;
import com.zhao.microheadlines.util.MD5Util;
import com.zhao.microheadlines.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/user/*")
public class NewsUserController extends BaseController{
    private NewsUserService newsUserService = new NewsUserServiceImpl();

    /**
     * check login status
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void checkLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getHeader("token");
        Result result = Result.build(null,ResultCodeEnum.NOTLOGIN);
        if (null!=token){
            if (!JwtHelper.isExpiration(token)){
                result = Result.ok(null);
            }
        }
        WebUtil.writeJson(resp,result);
    }

    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NewsUser registUser = WebUtil.readJson(req,NewsUser.class);
        Integer rows = newsUserService.registUser(registUser);
        Result result = Result.ok(null);
        if(rows==0){
            result = Result.build(null,ResultCodeEnum.USERNAME_USED);
        }
        WebUtil.writeJson(resp,result);
    }

    /**
     * check username
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void checkUserName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //get username from request
        String username = req.getParameter("username");
        //query username from database
        NewsUser user = newsUserService.findByUsername(username);

        Result result = Result.ok(null);
        if (null != user) {
            result = Result.build(null, ResultCodeEnum.USERNAME_USED);
        }
        WebUtil.writeJson(resp, result);
    }

    /**
     * get user information from token
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getUserInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getHeader("token");
        Result result = Result.build(null,ResultCodeEnum.NOTLOGIN);
        if(null != token && (!"".equals(token))){
            if(!JwtHelper.isExpiration(token)){
                Integer userId = JwtHelper.getUserId(token).intValue();
                NewsUser newsUser = newsUserService.findByUid(userId);
                if(null != newsUser){
                    Map data = new HashMap();
                    newsUser.setUserPwd("");
                    data.put("loginUser", newsUser);
                    result = result.ok(data);
                }
            }

        }
        WebUtil.writeJson(resp,result);
    }

    /**
     * login function
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //receive username and password
        NewsUser paramUser = WebUtil.readJson(req, NewsUser.class);
        NewsUser loginUser = newsUserService.findByUsername(paramUser.getUsername());
        Result result = null;
        if (null != loginUser) {
            if (MD5Util.encrypt(paramUser.getUserPwd()).equals(loginUser.getUserPwd())){
                Integer uid = loginUser.getUid();
                String token = JwtHelper.createToken(uid.longValue());
                Map data = new HashMap();
                data.put("token", token);
                result = Result.ok(data);

            }else{
                result = Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
            }
        }else {
            result = Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }
        WebUtil.writeJson(resp,result);
    }
}
