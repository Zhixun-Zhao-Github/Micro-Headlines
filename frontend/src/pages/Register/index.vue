<template>
  <div class="register-container">
    <el-form
      :model="registerForm"
      ref="formRef"
      label-width="150px"
      class="register-form"
      :rules="registerRules"
    >
      <h2>User Registration</h2>
          <el-form-item label="Nick name" prop="nickName">
          <el-input
            v-model="registerForm.nickName"
            autocomplete="off"
            ref="nickName"
            name="nickName"
            placeholder="Please enter a nickname"
          ></el-input>
        </el-form-item>
      <el-form-item label="Username" prop="username">
        <el-input
          v-model="registerForm.username"
          autocomplete="off"
          ref="username"
          name="username"
          placeholder="Please enter user name"
        ></el-input>
      </el-form-item>
      <el-form-item label="Password" prop="userPwd">
        <el-input
          type="password"
          v-model="registerForm.userPwd"
          ref="userPwd"
          name="userPwd"
          autocomplete="off"
          placeholder="Please enter your password"
        ></el-input>
      </el-form-item>
      <!-- prop="confirmPassword" -->
      <el-form-item label="Confirm Password"  prop="confirmPassword">
        <el-input
          type="password"
          v-model="registerForm.confirmPassword"
          autocomplete="off"
          ref="confirmPassword"
          name="confirmPassword"
          placeholder="Please confirm your password"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="register">Register</el-button>
        <el-button type="danger" @click="resetForm">Reset</el-button>
        <el-button type="success" @click="goLogin">Log in</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script lang="ts">
 import { defineComponent } from 'vue'
  export default  defineComponent({
    name:'Register'
  })
</script>
<script lang="ts" setup>
import { ref } from "vue"
import { useRouter } from 'vue-router'
import { ElMessage, FormInstance } from 'element-plus';
import { registerValidateApi, registerApi } from "../../api/index"
const router = useRouter()
// Initialize registration parameters
const registerForm = ref({
  username: "",
  userPwd: "",
  confirmPassword: "",
  nickName:''
})
const formRef = ref<FormInstance>()
  // Verification rules
const validateUsername = (rule: any, value: any, callback: any) => {
  if (value.length < 4) {
    callback(new Error('Username length cannot be less than 4 characters'))
  } else {
    callback()
  }
}
// Verification rules
const validatePassword = (rule: any, value: any, callback: any) => {
  if (value.length < 6) {
    callback(new Error('The password length cannot be less than 6 characters'))
  } else {
    callback()
  }
}
// Verification rules
const validateConfirmPassword = (rule: any, value: any, callback: any) => {
  if (value.length < 6) {
    callback(new Error('The password length cannot be less than 6 characters'))
  } else {
    callback()
  }
}
// Verification rules
const validateNickName = (rule: any, value: any, callback: any) => {
  if (value.length >= 2  && value.length  <= 30  ) {
    callback()
  } else {
    callback(new Error('The nickname length does not meet the rules'))
}
}
// Verification rules
const registerRules = {
  nickName: [{ required: true, trigger: 'blur', validator: validateNickName }],
  username: [{ required: true, validator: validateUsername }],
  userPwd: [{ required: true, trigger: 'blur', validator: validatePassword }],
  confirmPassword: [{ required: true, trigger: 'blur', validator: validateConfirmPassword }]
}

//Click the registered callback
const register = async () => {
  await formRef.value?.validate()
  if (registerForm.value.userPwd == registerForm.value.confirmPassword) {
    // Call the username verification interface
    await registerValidateApi(registerForm.value.username)
    //  Arrange parameters
    const obj = {
      username: "",
      userPwd: "",
      nickName: ''
    }
    obj.username = registerForm.value.username
    obj.userPwd = registerForm.value.userPwd
    obj.nickName = registerForm.value.nickName
    //  Call the registration interface
    await registerApi(obj)
    formRef.value?.resetFields()
    ElMessage.success("Registration success")
    } else {
      return ElMessage.error("Password and confirm password must be the same")
    }
    
}
//Click to log in callback
const goLogin = () => {
  router.push({path:"/login"})
}

//Click reset callback
const resetForm = () => {
  //Reset Form
  formRef.value?.resetFields()
}
  
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}
.register-form {
  width: 400px;
  text-align: center;
}
</style>
