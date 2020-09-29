<template>
  <div style="height:100%">
    <section style="margin:3% 0 3% 0.6%;height:100%">
      <div class="Echarts" style="height:100%">
        <div class="leftBox">
          <!-- 边框效果 -->
          <div class="panel">
            <h3 class="chart-title">{{msg}}</h3>
            <loadingSign v-if="isLoading" style="top: 69%;left: 13%;"></loadingSign>
            <div id="assChart1" style="width: 100%;height:100%;"></div>
            <!-- <div v-else class="noDataInfo">未获取到数据</div> -->
            <div class="panel-footer"></div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import loadingSign from "./loadingSign.vue";

export default {
  name: "chartTime",
  components: {
    loadingSign
  },
  data() {
    return {
      colors: ["#2EE7FF", "#86FED8", "#0f225E", "#8ac2f8c4"],
      isLoading: true,
      assData: null,
      haveData: true
    };
  },
  props: {
    msg: String,
    config: Object,
    data: Array
  },
  mounted() {
    // 生成图表，并监听resize事件，根据百分比变化图表
    const myChart = this.$echarts.init(document.getElementById("assChart1"));
    this.initEchart();
    window.addEventListener("resize", () => {
      myChart.resize();
    });
  },
  methods: {
    async initEchart(data) {
      //发请求时，开启loading特效
      this.isLoading = true;
      //设置时间
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
      //发起请求
      await this.$http.post("/RiskAsset/all", params).then(res => {
        if (res.data.code === 200) {
          //状态码200，关闭loading
          this.isLoading = false;
          this.assData = res.data.data;
          if (res.data.data.riskAssetVo1.data.length === 0) {
            this.haveData = false;
          }
          const myChart = this.$echarts.init(
            document.getElementById("assChart1")
          );
          //chart设置参数，具体配置项可参考echarts官网
          myChart.setOption({
            color: this.colors,
            tooltip: {
              trigger: "item",
              formatter: "{a} <br/>{b}: {c} ({d}%)"
            },
            legend: {
              orient: "vertical",
              left: 20,
              top:35,
              data: ["低风险", "已失陷", "高风险"],
              textStyle: {
                color: "white"
              }
            },
            series: [
              {
                name: "资产分布",
                type: "pie",
                radius: ["40%", "50%"],
                top: -40,
                left: 30,
                bottom: 10,
                avoidLabelOverlap: false,
                label: {
                  show: false,
                  position: "inside"
                },
                emphasis: {
                  label: {
                    show: true,
                    fontSize: "30",
                    fontWeight: "bold"
                  }
                },
                labelLine: {
                  show: false
                },
                data: this.assData.riskAssetVo2.list
              }
            ]
          });
        }
      });
    }
  }
};
</script>
<style scoped lang="scss">
#assChart2 {
  width: 60%;
  height: 15.5rem;
  top: -11.3rem;
  left: 11rem;
}
.noDataInfo {
  width: 100%;
  height: 100%;
  margin-top: -62%;
  color: #2ccbef;
  padding: 1% 34% 0 37%;
}
</style>
