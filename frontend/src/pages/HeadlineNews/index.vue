<template>
  <div class="container">
    <div class="listItem">
      <!-- Each item headline list -->
      <div class="containerItem" v-for="item in pageData" :key="item.hid">
        <div>
          <span class="text">{{ item.title }}</span>
        </div>
        
        <div>
          <el-button @click="toDetail(item.hid)" link
            style=" margin-left: 15px;margin-right: 50%; color: #67C23A">View Full Article</el-button>
          <el-button @click="Modify(item.hid)" v-if="item.publisher == type" link  style=" color : #E6A23C" round="true">Modify</el-button>
          <el-popconfirm confirm-button-text="Yes"  cancel-button-text="No" v-if="item.publisher == type" @confirm="handlerDelete(item.hid)" :title="`Are you sure you want to delete${item.title}?`">
            <template #reference>
              <el-button link round="true"  style=" color: #F56C6C">Delete</el-button>
            </template>
          </el-popconfirm>          
        </div>
        <div class="detail" style="text-align: right;">
          <span>{{ item.type == 1 ? "News":item.type == 2 ? "Sports": item.type == 3 ? "Entertainment": item.type == 4 ? "Technology" : "Other" }}</span>
          <span>{{item.pageViews}}Page views</span>
          <span>{{item.pastHours}}Hours ago</span>
        </div>
      </div>
  
      <!-- Pagination -->
      <div style="margin-top: 20px">
        <el-pagination 
          v-model:current-page="findNewsPageInfo.pageNum"
          v-model:page-size="findNewsPageInfo.pageSize" 
          @size-change="getPageList"
          @current-change="getPageList"
          :page-sizes="[5,7,10]" 
          background
          layout="prev, pager, next , ->, sizes, total" 
          :total="totalSize" />
      </div>
    </div>
  </div>
</template>

<script >
import { getfindNewsPageInfo , removeByHid } from "../../api/index"
 import { defineComponent } from 'vue'
  export default  defineComponent({
    name:'HeadlineNews'
  })
</script>
<script  setup>
import { ref, onMounted, getCurrentInstance, watch } from "vue"
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import pinia from '../../stores/index';
import { useUserInfoStore } from '../../stores/userInfo'
const  { Bus } = getCurrentInstance().appContext.config.globalProperties
const userInfoStore = useUserInfoStore(pinia)
const router = useRouter()
const type = userInfoStore.uid
const findNewsPageInfo = ref(
  {
    keyWords: "", // Search Title Keywords
    type: 0,           // News Type
    pageNum: 1,        // Page Number
    pageSize: 5,     // Page Size
  }
)
const totalSize = ref(0) //Total Number of Pages
// Initialize List Data
const pageData = ref([{
  hid: null,
  pageViews: null,
  pastHours: null,
  publisher: null,
  title: "",
  type: null
}])


//Receive data from the header component's user search.
Bus.on('keyword', (keywords) => {
  findNewsPageInfo.value.keyWords = keywords
})
// The "tid" passed when the header is clicked to switch highlights.
Bus.on('tid', (type) => {
  findNewsPageInfo.value.type = type
})
// Watch for changes in the initialization parameter "type". When "type" changes, resend the request to get the list data.
watch(() => findNewsPageInfo.value, () => {
  getPageList()
}, {
  deep: true,
})
// Initialize request for paginated list data.
const getPageList = async () => {
  let result = await getfindNewsPageInfo(findNewsPageInfo.value)
  pageData.value = result.pageInfo.pageData
 findNewsPageInfo.value.pageNum = result.pageInfo.pageNum
 findNewsPageInfo.value.pageSize = result.pageInfo.pageSize
 totalSize.value = +result.pageInfo.totalSize
}
// Lifecycle hooks for component mounting.
onMounted(() => {
  getPageList()
})
// Callback for clicking "View Full Article"
const toDetail = (hid) => {
  router.push({ name: "Detail" ,query:{ hid }});
}

// Callback for clicking "Delete"
const handlerDelete = async (id) => {
  await removeByHid(id)
  ElMessage.success('Deletion successful!')
  //Request to retrieve list again
  getPageList()
}
//Callback for clicking "Modify"
const Modify = (hid) => {
  router.push({ name: "addOrModifyNews", query: { hid } });
}
</script>

<style lang="less" scoped>
.container {
  width: 1200px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;

  // List style
  .listItem {
    .containerItem {
      margin-top: 20px;
      border-radius: 10px;
      border: 1px solid #ebebeb;
      width: 650px;
      height: 130px;
   

      div {
        margin-top: 10px;
      }

      .text {
        margin-left: 15px;
        color: #353a3f;
        font-weight:bold;
        font-size:1.1em;
      }

      .detail {
        span {
          margin-left: 15px;
          color: #b1b3b8;
          font-size:0.5em;
        }
      }
    }
  }
}
</style>
