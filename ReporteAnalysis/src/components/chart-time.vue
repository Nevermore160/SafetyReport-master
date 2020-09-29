<template>
    <div class="leftBox">
      <div class="panel">
        <h3 class="chart-title">{{msg}}</h3>
        <loadingSign v-if="isLoading" style="top: 33%;left: 13%;"></loadingSign>
        <div id="chart1" style="width: 100%;height:100%;"></div>
      </div>
  </div>
</template>

<script>
import loadingSign from "./loadingSign.vue";

const cors = require("cors");

export default {
  name: "chartTime",
  components: {
    loadingSign
  },
  data() {
    return {
      colors: ["#86fed8", "#14e9ff", "#015bff"],
      timeData: [],
      data: null,
      isLoading: true,
      haveData: true
    };
  },
  props: {
    msg: String
  },
  mounted() {
    if (this.data == null) {
      this.$Loading.start();
      this.initEchart();
    } else {
      this.$Loading.finish();
    }

    const myChart = this.$echarts.init(document.getElementById("chart1"));
    window.addEventListener("resize", () => {
      myChart.resize();
    });
  },
  methods: {
    async initEchart(data) {
      //开启loading
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
      await this.$http.post("/DistributionTrend/all", params).then(res => {
        if (res.data.code == 200) {
          //状态码200,关闭请求
          this.isLoading = false;
          this.data = res.data.data;
          this.timeData = res.data.data.list;
          //截取时间到分钟部分,秒钟不显示
          for (let i = 0; i < this.timeData.length; i++) {
            this.timeData[i] = this.timeData[i].substr(0, 16);
          }
          //如果返回数据长度为0,那么当作没有没有数据
          if (res.data.data.high.length == 0) {
            this.haveData = false;
          }
          //初始化表格
          const myChart = this.$echarts.init(document.getElementById("chart1"));
          //时间处理
          const timeData = this.timeData.map(str => str.replace("2020-", ""));
          //配置图表项,具体请查询echarts官网
          const option = {
            color: this.colors,
            tooltip: {
              trigger: "axis",
              axisPointer: {
                type: "cross",
                label: {
                  backgroundColor: "#6a7985"
                },
                lineStyle: {
                  color: this.colors[0]
                }
              }
            },
            legend: {
              data: ["高风险", "中风险", "低风险"],
              textStyle: { color: "#fff" },
              inactiveColor: this.colors[2]
            },
            grid: {
              left: "8%",
              right: "8%",
              top:"11%",
              bottom: "20%",
              containLabel: true
            },

            xAxis: [
              {
                type: "category",
                boundaryGap: false,
                data: timeData,
                axisTick: {
                  alignWithLabel: true
                },
                axisLine: {
                  onZero: false,
                  lineStyle: {
                    color: this.colors[1]
                  }
                }
              },
              {
                type: "category",
                axisTick: {
                  alignWithLabel: true
                },
                axisLine: {
                  onZero: false,
                  lineStyle: {
                    color: this.colors[0]
                  }
                },
                data: timeData
              }
            ],
            yAxis: [
              {
                axisLine: {
                  type: "value",
                  lineStyle: {
                    color: this.colors[0]
                  }
                }
              }
            ],

            series: [
              {
                name: "高风险",
                type: "line",
                stack: "总量",
                areaStyle: {},
                smooth: true,
                data: this.data.high
              },
              {
                name: "中风险",
                type: "line",
                stack: "总量",
                areaStyle: {},
                smooth: true,
                data: this.data.medium
              },
              {
                name: "低风险",
                type: "line",
                stack: "总量",
                areaStyle: {},
                smooth: true,
                data: this.data.low
              }
            ]
          };
          myChart.setOption(option);
        }
      });
    }
  }
};
</script>
<style scoped lang="scss">
.noDataInfo {
  width: 100%;
  height: 100%;
  margin-top: 5%;
  color: #2ccbef;
  padding: 1% 34% 0 37%;
}
</style>
