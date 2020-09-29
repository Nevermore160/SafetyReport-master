<template>
  <div ref="bg" class="test" :class="{'noImage': !data_IsImage}">
    <pointwave :color="0x097bdb" style="height:0" />
    <div class="container">
      <div class="menu-l">
        <Button type="info" ghost @click="$router.push('/')" style="margin-left:1.4rem">总体预览</Button>
        <Button
          class="choosePart"
          type="info"
          ghost
          @click="$router.push('/timetrend')"
          style="margin-left:1.4rem"
        >时间趋势</Button>
        <Button
          class="choosePart"
          type="info"
          ghost
          @click="$router.push('/assets')"
          style="margin-left:1.4rem"
        >资产分布</Button>
      </div>
      <div class="menu-r">
        <Button
          class="choosePart"
          type="info"
          ghost
          @click="$router.push('/victims')"
          style="margin-left:1.4rem"
        >受害者</Button>
        <Button
          class="choosePart"
          type="info"
          ghost
          @click="$router.push('/attackers')"
          style="margin-left:1.4rem"
        >攻击者</Button>
        <Button
          class="choosePart"
          type="info"
          ghost
          @click="$router.push('/topn')"
          style="margin-left:1.4rem"
        >TOPN</Button>
      </div>
      <div class="pheader">分析报告可视化面板展示</div>
      <div class="body">
        <div class="tools" ref="tools">
          <DatePicker
            type="datetimerange"
            :disabled-seconds="seconds"
            ref="time"
            format="yyyy-MM-dd HH:mm:ss"
            @on-change="time=$event"
            v-model="time"
            placeholder="选择时间"
            style="width: 8rem"
          ></DatePicker>
          <Button type="info" ghost @click="reDraw" style="margin:.7rem" v-if="!isFullScreen">生成报表</Button>
          <Button
            type="info"
            ghost
            href="http://10.11.40.91:8080/report/download"
            @click="exportFile"
            style="margin-left:0.1rem"
            v-if="!isFullScreen"
          >导出</Button>
          <loadingSign v-if="isLoading" style="width: 3rem;height: 3rem; top: 3%;left: 89%;"></loadingSign>
          <Button
            type="info"
            ghost
            v-if="!isFullScreen"
            @click="getFullScreen"
            style="margin-left:.8rem"
          >全屏显示</Button>
          <Button
            type="info"
            ghost
            size="large"
            v-if="isFullScreen"
            @click="leaveFullScreen"
            style="margin-left:1.4rem"
          >退出全屏</Button>
        </div>
        <router-view ref="content" :key="$route.path"></router-view>
      </div>
    </div>
  </div>
</template>
<script>
import Pointwave from "@/components/Pointwave";
import loadingSign from "@/components/loadingSign";
import bgPic from "@/assets/images/bg1.png";
export default {
  components: {
    Pointwave,
    loadingSign
  },
  data() {
    return {
      color: "",
      time: null,
      seconds: [
        0,
        1,
        2,
        3,
        4,
        5,
        6,
        7,
        8,
        9,
        10,
        11,
        12,
        13,
        14,
        15,
        16,
        17,
        18,
        19,
        20,
        21,
        22,
        23,
        24,
        25,
        26,
        27,
        28,
        29,
        30,
        31,
        32,
        33,
        34,
        35,
        36,
        37,
        38,
        39,
        40,
        41,
        42,
        43,
        44,
        45,
        46,
        47,
        48,
        49,
        50,
        51,
        52,
        53,
        54,
        55,
        56,
        57,
        58,
        59,
        60
      ],
      isLoading: false,
      timeData: null,
      data_IsImage: true,
      isFullScreen: false,
      timeData: null
    };
  },
  created() {
     document.title = '日志分析生成报告项目'
  },
  methods: {
    changeTime() {},
    reDraw() {
      let path = this.$route.path;
      if (this.time[0] != "" || this.time != null) {
        const [startTime, endTime] = this.time;
        this.timeData = { startTime, endTime };
      } else {
        this.timeData = { startTime: null, endTime: null };
      }
      if (path === "/") {
        this.$refs.content.$refs.asset.initEchart();
        this.$refs.content.$refs.map.initAttEchart();
        this.$refs.content.$refs.map.initVicEchart();
        this.$refs.content.$refs.topn.initEchart();
        this.$refs.content.$refs.time.initEchart();
        this.$refs.content.$refs.suggest.initSuggest();
      } else if (path === "/victims") {
        this.$refs.content.$refs.vicBig.__vue__.initEchart();
      } else if (path === "/attackers") {
        this.$refs.content.$refs.attBig.__vue__.initEchart();
      } else if (path === "/topn") {
        this.$refs.content.$refs.topnBig.__vue__.initEchart();
      } else if (path === "/assets") {
        this.$refs.content.$refs.assetBig.__vue__.initEchart();
      } else if (path === "/timetrend") {
        this.$refs.content.$refs.timeBig.__vue__.initEchart();
      }
    },
    async exportFile() {
      // window.location.href = "http://47.115.43.39:8080/report/download/";
      this.isLoading = true;
      if (this.time[0] === "")
        var params = {
          startTime: null,
          endTime: null
        };
      else if (this.timeData.startTime === undefined)
        params = {
          startTime: null,
          endTime: null
        };
      else {
        params = {
          startTime: this.timeData.startTime,
          endTime: this.timeData.endTime
        };
      }
      await this.$http
        .post("/report/download", null, { responseType: "blob" })
        .then(res => {
          var blob = new Blob([res.data]);
          var downloadElement = document.createElement("a");
          var href = window.URL.createObjectURL(blob); //创建下载的链接
          downloadElement.href = href;
          downloadElement.download = "用户数据分析报告.docx"; //下载后文件名

          this.isLoading = false;
          document.body.appendChild(downloadElement);
          downloadElement.click(); //点击下载
          document.body.removeChild(downloadElement); //下载完成移除元素
          window.URL.revokeObjectURL(href); //释放掉blob对象
        });
    },

    getFullScreen() {
      this.isFullScreen = true;
      this.FullCreeen(document.documentElement);
    },
    leaveFullScreen() {
      this.isFullScreen = false;
      this.outFullCreeen(document);
    },
    FullCreeen(element) {
      this.$refs.tools.style.margin = "2% 0";
      let el = element;
      let rfs =
        el.requestFullScreen ||
        el.webkitRequestFullScreen ||
        el.mozRequestFullScreen ||
        el.msRequestFullScreen;
      if (typeof rfs != "undefined" && rfs) {
        rfs.call(el);
      } else if (typeof window.ActiveXObject != "undefined") {
        let wscript = new ActiveXObject("WScript.Shell");
        if (wscript != null) {
          wscript.SendKeys("{F11}");
        }
      }
    },
    //退出全屏的方法
    outFullCreeen(element) {
      this.$refs.tools.style.margin = "0";
      let el = element;
      let cfs =
        el.cancelFullScreen ||
        el.webkitCancelFullScreen ||
        el.mozCancelFullScreen ||
        el.exitFullScreen;
      if (typeof cfs != "undefined" && cfs) {
        cfs.call(el);
      } else if (typeof window.ActiveXObject != "undefined") {
        let wscript = new ActiveXObject("WScript.Shell");
        if (wscript != null) {
          wscript.SendKeys("{F11}");
        }
      }
    }
  }
};
</script>
<style scoped>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
.container {
  height: 100%;
  width: 100%;
  display: flex;
  flex-direction: column;
}
.noImage {
  background-image: none;
}
.logo {
  width: 4rem;
  height: 2rem;
  top: -1rem;
  position: relative;
  left: 42%;
}
.tools {
  width: 100%;
  text-align: center;
  position: relative;
}

.menu-l {
  position: fixed;
  left: 6%;
  top: 12%;
  z-index: 9999;
}
.menu-r {
  position: fixed;
  right: 6%;
  top: 12%;
  z-index: 9999;
}
.body {
  flex: 1;
  height: 0;
  width: 100%;
  padding: 0 3.5rem 2.7rem 3.5rem;
  display: flex;
  flex-direction: column;
}
</style>
