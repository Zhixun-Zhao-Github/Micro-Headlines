import axios from "axios";
import { ElMessage } from 'element-plus';
import pinia from '../stores/index';
import { useUserInfoStore } from '../stores/userInfo';
import NProgress from "nprogress";
import "nprogress/nprogress.css";
// Configure a new axios instance
const service = axios.create({
  baseURL: "/app-dev/",
  timeout: 50000,
});

// Add a request interceptor
service.interceptors.request.use((config) => {
  NProgress.start()//Enable progress bar
  // If there is a token, it is carried to the backend through the request header
  const userInfoStore = useUserInfoStore(pinia) // If it is not called in a component, pinia must be passed in
  const token = userInfoStore.token
     if (token) {
      // config.headers['token'] = token  // Error: The headers object does not declare a token, so it cannot be added casually
      (config.headers)['token'] = token
    }
  return config;
});

// Adding a response interceptor
service.interceptors.response.use(
  (response) => {
  NProgress.done()//Close progress bar

    if(response.data.code !== 200){
    // Determine the response status code
    if (response.data.code == 501)  return  Promise.reject(ElMessage.error("Incorrect username"))
    else if (response.data.code == 503) return  Promise.reject(ElMessage.error("Wrong password"))
    else if (response.data.code == 504) return  Promise.reject(ElMessage.error("Login expired"))
    else if (response.data.code == 505) return  Promise.reject(ElMessage.error("Username occupied"))
    } else {
      return response.data.data; /* Returns the data attribute data in the successful response data */
    }
  },
  (error) => {
  NProgress.done()//Close progress bar
    return Promise.reject(error.message);
  }
);

export default service;
