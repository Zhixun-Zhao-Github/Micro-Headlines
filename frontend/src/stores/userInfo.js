
import { defineStore } from 'pinia';
import { getToken, removeToken, setToken } from '../utils/token-utils';
import { getLogin,getUserInfo } from '../api/index';


/**
 * User Info
 * @methods setUserInfos 
 */
export const useUserInfoStore = defineStore('userInfo', {

	state: () => ({
    token: getToken(),
    nickName: '',
    uid: '',
  }),

	actions: {
    // Login asynchronous action
    async login (loginForm) {
       // Send a login request
      const result = await getLogin(loginForm)
      // After the request is successful, take out the token and save it in pinia and local
      const token = result.token
      
      this.token = token
      setToken(token)
    },
    async getInfo () {
      const result = await getUserInfo()
      this.nickName = result.loginUser.nickName
      this.uid = result.loginUser.uid
    },
    initUserInfo(){
      removeToken()
      this.nickName = ""
      this.uid = ""
      console.log('1111111111');
      
    }

  },
  

});