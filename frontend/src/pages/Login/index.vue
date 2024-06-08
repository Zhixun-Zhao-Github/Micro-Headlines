<template>
  <div class="login-container">
    <el-form
      :model="loginForm"
      ref="formRef"
      label-width="80px"
      class="login-form"
      :rules="loginRules" 
    >
      <h2>User Login</h2>
      <el-form-item label="Username" prop="username">
        <el-input
          v-model="loginForm.username"
          ref="username"
          name="username"
          autocomplete="off"
          placeholder="Please enter username"
        ></el-input>
      </el-form-item>
      <el-form-item label="Password" prop="userPwd">
        <el-input
          type="password"
          v-model="loginForm.userPwd"
          autocomplete="off"
          placeholder="Please enter your password."
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="success" @click.native.prevent="login">Log In</el-button>
        <el-button type="primary" @click="toRegister">Register</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script lang="ts">
 import { defineComponent } from 'vue'
  export default  defineComponent({
    name:'Login'
  })
</script>
<script lang="ts" setup>
import { ref } from "vue"
import { useUserInfoStore } from '../../stores/userInfo';

import type { FormInstance } from 'element-plus';
import { useRouter } from 'vue-router'
const userInfoStore = useUserInfoStore()
const router = useRouter()
const formRef = ref<FormInstance>()
const loading = ref(false)
//Account and Password Parameters
const loginForm = ref({
      username: "Zhixun Zhao",
      userPwd: "123456",
})
// Validation Rules
const validateUsername = (rule: any, value: any, callback: any) => {
  if (value.length < 4) {
    callback(new Error('Username length must be at least 4 characters.'))
  } else {
    callback()
  }
}
// Validation Rules
const validatePassword = (rule: any, value: any, callback: any) => {
  if (value.length < 6) {
    callback(new Error('Password length must be at least 6 characters.'))
  } else {
    callback()
  }
}
// Validation Rules
const loginRules = {
  username: [{ required: true, validator: validateUsername }],
  userPwd: [{ required: true, trigger: 'blur', validator: validatePassword }]
}
//Login button callback
const login = async () => {
  // console.log('Click Log In');
  await formRef.value?.validate()
  loading.value = true
  try {
    // await getUserInfo(loginForm.value)
    await userInfoStore.login(loginForm.value)
    router.push({ name: "HeadlineNews" });
  } finally {
    loading.value = false
  }
  // loading.value = true
  // const { username, userPwd } = loginForm.value
  // try {
  //   await userInfoStore.login(username, userPwd)
  //   router.push({ path: redirect.value || '/' })
  // } finally {
  //   loading.value = false
  // }
}

const toRegister = ()=> {
  router.push({ name: "Register" });
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}
.login-form {
  width: 400px;
  text-align: center;
}
</style>
