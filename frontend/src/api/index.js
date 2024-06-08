import request from "../utils/request/"

// portal/findAllTypes
//Get category list
export const getfindAllTypes = () => {
  return request.get("portal/findAllTypes");
};
// Pagination with conditions to query all headlines
export const getfindNewsPageInfo = (info) => {
  return request.post("portal/findNewsPage",info);
};
// View headline details
export const getshowHeadlineDetail = (id) => {
    return request({
        method: "post",
        url: "portal/showHeadlineDetail",
        headers: {
        "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
        },
         data:`hid=${id}`
  });
};

//Delete callback
// headline/removeByHid
export const removeByHid = (id) => {
    return request({
         method: "post",
            url: "headline/removeByHid",
            headers: {
            "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
            },
            data:`hid=${id}`
  })
};

//Login interface
export const getLogin = (info) => {
  return request.post("user/login",info);
};
//API for obtaining user information
export const getUserInfo = (info) => {
  return request.get("user/getUserInfo");
};

//Registration verification interface  user/checkUserName
export const registerValidateApi = (username) => {
    return request({
         method: "post",
            url: "user/checkUserName",
            headers: {
            "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
            },
            data:`username=${username}`
  })
};

// Registered interface
export const registerApi = (userInfo) => {
  return request.post("user/regist",userInfo)
}
//Interface for determining user login expiration
export const isUserOverdue = () => {
  return request.get("user/checkLogin")
}

// Modify the interface for headline echo
export const getFindHeadlineByHid = (id) => {
    return request({
        method: "post",
        url: "headline/findHeadlineByHid",
        headers: {
        "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
        },
         data:`hid=${id}`
  });
};

//Click Save Modified Callback
// headline/update
export const saveOrAddNews = (news) => {
  return request.post("headline/update",news)
}

// headline/publish
export const issueNews = (news) => {
  return request.post("headline/publish",news)
}

