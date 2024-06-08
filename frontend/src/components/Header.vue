<template>
  <div class="headerContainer">
    <!-- Left side of head -->
      <div>
        <img src="../assets/logo.png" alt="Logo" height="60"/>
      </div>
    <div class="left">
      <ul>
        <li @click="HighlightHandler(index,)"  v-for="(item,index) in findAllTypeList" :key="item.tid">
          <a :class="{ active: item.isHighlight }" href="javascript:;">{{item.tname}}</a>
        </li>
      </ul>
    </div>
    <!-- Right side of head -->
    <div class="right" >
      <div class="rightInput" style="margin-right: 15px; margin-left: 100px;">
        <el-input v-model="keywords" placeholder="Search the latest headlines"></el-input>
        <!-- <el-button   type="primary">search</el-button> -->
      </div>

  
      <!-- Display after user login -->
      <div class="btn-dropdown">
      <!-- Display when the user is not logged in -->
     
      <div v-if="nickName" style="display: flex; justify-content: center; align-items: center;">
             <el-dropdown>
          <el-button type="primary">
            Hello:{{ nickName }}<el-icon class="el-icon--right"><arrow-down /></el-icon>
          </el-button>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="handlerNews">Publish News</el-dropdown-item>
              <el-dropdown-item>Personal Center</el-dropdown-item>
              <el-dropdown-item>Browsing History</el-dropdown-item>
              <el-dropdown-item @click="Logout">Sign Out</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
       <div v-else class="containerButton">
          <el-button  style="background: #409EFF; color: #FFFFFF" @click="toLogin">Log In</el-button>
          <el-button  style="background: #409EFF; color: #FFFFFF" @click="toRegister">Register</el-button>
        </div>
      
      </div>
    </div>
  </div>
</template>

<script>
import { defineComponent } from 'vue'
export default defineComponent({
  name: 'Header'
})
</script>

<script setup>
import { getfindAllTypes, isUserOverdue } from '../api/index'
import { ref, onMounted , getCurrentInstance ,watch, onUpdated} from "vue"
import { useRouter } from 'vue-router'
import { ArrowDown } from '@element-plus/icons-vue'
import { removeToken } from '../utils/token-utils' 
import pinia from '../stores/index';
import { useUserInfoStore } from '../stores/userInfo'
const userInfoStore = useUserInfoStore(pinia)
const nickName = ref("")
// Get the global event bus
const { Bus } = getCurrentInstance().appContext.config.globalProperties
const router = useRouter()
const keywords = ref("") // Collect and search the latest headlines parameters
//Monitor changes in search parameters and pass data to the HeadlineNews component when the search parameters change
watch(keywords, (newVal) => {
  Bus.emit('keyword', newVal)
})
const findAllTypeList = ref([])//All Headlines Categories
const toLogin = () => {
router.push({ name: "Login" });
}
//Click to go to the registration page
const toRegister = () => {
  router.push({ name: "Register" });
}
const getList = async () => {
  let result = await getfindAllTypes()
  // Add highlight mark when traversing data
  result.forEach((item) => {
    item.tid = item.tid
    item.tname = item.tname
    item.isHighlight = false
  })
  // Add microheadlines data
  result.unshift({
    isHighlight: true,
    tid: 0,
    tname: "MircoHeadlines"
  })
  findAllTypeList.value = result
}
// Page mount lifecycle callback
onUpdated(() => {
  nickName.value = userInfoStore.nickName
})
onMounted(() => {
  getList()
})

//Click to switch the highlighted callback (exclusive idea)
const HighlightHandler = (index) => {
  findAllTypeList.value.forEach((item) => {
    item.isHighlight = false
  })
  // When switching highlight, pass tid to the HeadlineNews component
  findAllTypeList.value[index].isHighlight = true
  Bus.emit('tid', findAllTypeList.value[index].tid)
}

// Click the callback to log out
const Logout = () => {
  removeToken()
  userInfoStore.initUserInfo()
  nickName.value = ""
  router.go({ name: "HeadlineNews" });
}

//Click to publish news callback
const handlerNews = async () => {
  //Send a request to determine whether the user's token has expired
  await isUserOverdue()
  router.push({ name: "addOrModifyNews" });
}
</script>

<style>
.el-dropdown {
  vertical-align: top;
  width: 100px;
}

.el-dropdown+.el-dropdown {
  margin-left: 15px;
}

.el-icon-arrow-down {
  font-size: 12px;
}
</style>

<style lang="less" scoped>
.headerContainer {
  width: 100%;
  height: 60px;
  background: #409EFF;
  display: flex;
  justify-content: start;
  .left {
    ul {
      display: flex;
      li {
        list-style: none;
        margin-left: 20px;
        a:-webkit-any-link {
          text-decoration: none;
          color: #FFFFFF;
          &.active {
            color: #FFFFFF;
            font-size:1.3em;
            font-weight:bold;
          }
        }
      }
    }
  }
  .right {
   
    .containerButton {
      display: flex;
      align-items: center;
    }
    line-height: 60px;
    display: flex;
    flex-wrap: nowrap;
    .rightInput {
      display: flex;
       align-items: center;
      :deep(.el-input__inner) {
        height: 30px;
        width: 180px;
      }
    }
    .btn-dropdown{
      display: flex;
      align-items: center;
    }
    :deep(.el-button) {
      margin: 0 0 0 10px;
      display: flex;
      justify-content: center;
      align-items: center;
    }
  }
}


.example-showcase .el-dropdown + .el-dropdown {
  margin-left: 15px;
}
.example-showcase .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}
</style>


















