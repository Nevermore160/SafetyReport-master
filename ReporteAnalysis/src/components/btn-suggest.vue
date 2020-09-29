<template>
  <div>
    <div class="panel">
      <h3 class="chart-title" style="padding-left:36%;" @click="checkSg">{{msg}}</h3>
      <div class="panel-footer"></div>
    </div>
    <Section class="sgDialog" v-if="showDialog">
      <div class="sgBox">
        <div class="panel" style="margin:2rem;height:85%;width:91%">
          <loadingSign v-if="isLoading" style="top: 41%;left: 47%;"></loadingSign>
          <div v-if="haveData">
            <!-- 数据太多无法展示，暂时只展示两条 -->
            <div v-for="item in info.slice(0,2)">
              <!-- v-for对传过来的建议数据进行循环 -->
              <h4
                style="color:white;margin:auto;padding:1rem 0 0 27%"
              >针对告警子类型{{item.subCategory}}的建议</h4>
              <p v-html="item.suggestion" style=" white-space:pre-wrap;"></p>
            </div>
          </div>
          <!-- 如果没有数据，就显示未获取到数据 -->
          <div v-else class="noDataInfo">未获取到数据</div>

          <div class="panel-footer"></div>
          <Button type="info" ghost size="large" @click="showDialog=false">关闭</Button>
        </div>
      </div>
    </Section>
  </div>
</template>
<script>
import loadingSign from "./loadingSign.vue";

export default {
  data() {
    return {
      showDialog: false,
      info: [],
      isLoading: true,
      haveData: true
    };
  },
  mounted() {
    this.initSuggest();
  },
  components: {
    loadingSign
  },
  props: {
    msg: String
  },
  methods: {
    async checkSg() {
      //发请求时，开启loading特效
      this.isLoading = true;
      //判断当前建议弹窗是否显示
      this.showDialog === true
        ? (this.showDialog = false)
        : (this.showDialog = true);
      //发起请求
      this.initSuggest();
    },
    async initSuggest() {
      //获取父组件的开始时间和结束时间
      if (this.$parent.$parent.time[0] === "")
        var params = {
          startTime: null,
          endTime: null
        };
      else if (this.$parent.$parent.timeData.startTime === undefined)
        params = {
          startTime: null,
          endTime: null
        };
      else {
        params = {
          startTime: this.$parent.$parent.timeData.startTime,
          endTime: this.$parent.$parent.timeData.endTime
        };
      }
      await this.$http.post("/suggestion/all", params).then(res => {
        if (res.data.code === 200) {
          //如果状态码为200，取消loading动效
          this.isLoading = false;
          this.info = res.data.data.suggestionVo1s;
          if (res.data.data.suggestionVo1s.length == 0) {
            this.haveData = false;
          }
        }
      });
    }
  }
};
</script>
<style lang="scss" scoped>
.noDataInfo {
  width: 100%;
  height: 100%;
  margin-top: 30%;
  color: #2ccbef;
  padding: 1% 34% 0 43%;
}
</style>
