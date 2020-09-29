<template>
  <div style="height:100%" ref="timeBig">
    <div class="timeBox">
      <div class="panel">
        <h2 class="chart-title">数据时序分布趋势图</h2>
        <loadingSign v-if="isLoading" style="top: 50%;left: 47%;"></loadingSign>
        <div id="charttime" style="width: 100%;height:88%;"></div>
      </div>
      <div class="panel-footer"></div>
    </div>
  </div>
</template>
<script>
import loadingSign from "../components/loadingSign";

export default {
  data() {
    return {
      colors: ["#86fed8", "#14e9ff", "#015bff"],
      timeData: [],
      data: null,
      isLoading: true
    };
  },
  components: {
    loadingSign
  },
  mounted() {
    //初始化图表
    this.initEchart();
    //监听resize事件
    const myChart = this.$echarts.init(document.getElementById("charttime"));
    window.addEventListener("resize", () => {
      myChart.resize();
    });
  },
  methods: {
    async initEchart(data) {
      //开启loading特效
      this.isLoading = true;
      //处理时间范围
      if (this.$parent.time[0] === "")
        var params = {
          startTime: null,
          endTime: null
        };
      else if (this.$parent.timeData.startTime === undefined)
        params = {
          startTime: null,
          endTime: null
        };
      else {
        params = {
          startTime: this.$parent.timeData.startTime,
          endTime: this.$parent.timeData.endTime
        };
      }
      //发起请求
      await this.$http.post("/DistributionTrend/all", params).then(res => {
        if (res.data.code === 200) {
          //状态码200,关闭loading
          this.isLoading = false;
          this.data = res.data.data;
          this.timeData = res.data.data.list;
          //初始图表
          const myChart = this.$echarts.init(
            document.getElementById("charttime")
          );
          for (let i = 0; i < this.timeData.length; i++) {
            this.timeData[i] = this.timeData[i].substr(0, 16);
          }
          const timeData = this.timeData.map(str => str.replace("2020/", ""));
          //设置参数
          myChart.setOption({
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
              left: "3%",
              right: "4%",
              bottom: "15%",
              containLabel: true
            },
            xAxis: [
              {
                type: "category",
                boundaryGap: false,
                data: timeData,
                axisLine: {
                  onZero: false,
                  lineStyle: {
                    color: this.colors[0]
                  }
                },
                splitLine: {
                  show: false
                }
              },
              {
                type: "category",
                boundaryGap: false,
                data: timeData,
                axisLine: {
                  show: false,
                  onZero: false,
                  lineStyle: {
                    color: this.colors[0]
                  }
                },
                splitLine: {
                  show: false
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
                  },
                  splitLine: {
                    show: false
                  }
                }
              }
            ],
            dataZoom: [
              {
                show: true,
                realtime: true,
                start: 30,
                end: 70
              },
              {
                type: "inside",
                realtime: true,
                start: 30,
                end: 70
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
          });
        }
      });
    }
  }
};
</script>
<style lang="scss" scoped>
</style>
