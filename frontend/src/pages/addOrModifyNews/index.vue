<template>
  <el-card class="box-card AddNewsContainer">
    <el-form :rules="newsRules" :model="formData" ref="formRef" size="default">
      <el-form-item label="Article Title" prop="title">
        <el-input v-model="formData.title" placeholder="Please enter the title."></el-input>
      </el-form-item>
      <el-form-item style="margin: 50px 0;" label="Article Content" prop="article">
        <el-input v-model="formData.article" type="textarea" rows="8"></el-input>
      </el-form-item>
      <el-form-item label="Article Category"  prop="type">
        <el-select v-model="formData.type" placeholder="Please select the article category.">
          <el-option v-for="item in article" :label="item.name" :value="item.type">
          </el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <el-form-item>
      <el-button   @click="handlerCancel">Cancel</el-button>
      <el-button type="primary"  @click="handlerSave">Save</el-button>
    </el-form-item>
  </el-card>
</template>

<script>
import { defineComponent } from 'vue'
import { isUserOverdue } from '../../api/index'
export default defineComponent({
  name: 'AddNews'
})
</script>
<script  setup>
import { getFindHeadlineByHid , saveOrAddNews, issueNews } from "../../api/index"
import { ref, onMounted } from "vue"
import { useRoute } from 'vue-router'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
const router = useRouter() 
const route = useRoute() 


const formRef = ref()
// Validation Rules
const validateType = (rule, value, callback) => {
  if (value.length) {
    callback()
  } else {
    callback(new Error('Article title is required.'))
  }
}
// Validation Rules
const validateArticle = (rule, value, callback) => {
  if (value.length) {
    callback()
  } else {
    callback(new Error('Article content is required.'))
  }
}
// Validation Rules
const validateTitle = (rule, value, callback) => {
  if (value.length) {
    callback()
  } else {
    callback(new Error('Article category is required.'))
}
}
// Validation Rules
const newsRules = {
  title: [{ required: true, trigger: 'blur', validator: validateTitle }],
  article: [{ required: true, trigger: 'blur', validator: validateArticle }],
  type: [{ required: true, validator: validateType }],
}


const formData = ref({
  hid:null,
  title: "",   // Article Title
  article: "", // Article Content
  type: ""     // Article Category
})
//Initialize article category data
const article = [
  {
    type: "1",
    name: "News"
  },
  {
    type: "2",
    name: "Sports"
  },
  {
    type: "3",
    name: "Entertainment"
  },
  {
    type: "4",
    name: "Technology"
  },
  {
    type: "5",
    name: "Other"
  }
]
// If it's a modification click, the route will carry the "hid" parameter, and a request should be sent to retrieve data for display.
const clickModifyEcho = async () => {
  if (!route.query.hid)  return
    let result = await getFindHeadlineByHid(route.query.hid)
    formData.value.title = result.headline.title
  formData.value.article = result.headline.article
     
    formData.value.type = result.headline.type === 1 ? "News" : result.headline.type === 2 ? "Sports" : result.headline.type === 3 ? "Entertainment" : result.headline.type === 4 ? "Technology" : "Other" 
}
//Page Mounting Lifecycle
onMounted(() => {
  clickModifyEcho()
})
//Callback for clicking "Cancel"
const handlerCancel = () => {
  router.back()
}
//Callback for clicking "Save"
const handlerSave = async () => {
  await formRef.value?.validate()
    //Send a request to check if the user's token has expired.
  await isUserOverdue()
const Obj = {...formData.value}

  //Organize request parameters
//  Obj.hid = userInfoStore.uid.toString()  //Add user ID to let the backend know who added it.
 Obj.hid = route.query.hid  //Add user ID to let the backend know who added it.
// Determine the type.
 if(Obj.type == "News" ) Obj.type = "1"
 if(Obj.type == "Sports" ) Obj.type = "2"
 if(Obj.type == "Entertainment" ) Obj.type = "3"
 if(Obj.type == "Technology" ) Obj.type = "4"
 if (Obj.type == "Other") Obj.type = "5"
  //send request
  if (route.query.hid) {
    await saveOrAddNews(Obj)
    ElMessage.success("Successfully modified")
  }
  else {
    await issueNews(formData.value)
    ElMessage.success("Added successfully")
    }
    router.push({ name: "HeadlineNews" });
}


</script>

<style lang="less" scoped>
.AddNewsContainer {
  width: 600px;
  margin: 150px auto;
}
</style>
