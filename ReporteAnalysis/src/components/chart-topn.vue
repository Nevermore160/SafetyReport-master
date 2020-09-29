<template>
  <div class="rightBox">
    <div class="panel">
      <h3 class="chart-title">攻击链TOP5</h3>
      <loadingSign v-if="isLoading" style="top:31%;left:81%"></loadingSign>
      <div id="chartTopn1" class="topnItem" style="width: 100%;height:37%;"></div>
      <h3 class="chart-title">被害设备TOP5</h3>
      <loadingSign v-if="isLoading" style="top:62%;left:81%"></loadingSign>
      <div id="chartTopn2" class="topnItem" style="width: 100%;height:37%;"></div>
      <div class="panel-footer"></div>
    </div>
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
      isLoading: true
    };
  },
  props: {
    msg: String
  },
  mounted() {
    this.initEchart();
    //初始化图表
    const myChart = this.$echarts.init(document.getElementById("chartTopn1"));
    const myChart2 = this.$echarts.init(document.getElementById("chartTopn2"));
    //监听resize事件
    window.addEventListener("resize", () => {
      myChart.resize();
      myChart2.resize();
    });
  },
  methods: {
    async initEchart() {
      //开启loading动效
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
      await this.$http.post("/Attacker/topN", params).then(res => {
        if (res.data.code == 200) {
          //状态码200,关闭动效
          this.isLoading = false;
          this.data = res.data.data;
          //初始化图表
          const myChart = this.$echarts.init(
            document.getElementById("chartTopn1")
          );
          const myChart2 = this.$echarts.init(
            document.getElementById("chartTopn2")
          );
          //放进chartArray方便循环
          const chartArray = [myChart, myChart2];
          //循环处理图表
          for (let i = 0; i < chartArray.length; i++) {
            const { data } = this.data.topN[i];
            const titlename = this.data.topN[i].titleName;
            const { valdata } = this.data.topN[i];
            //颜色设置
            const myColor = [
              "#2EE7FF",
              "#86FED8",
              "#8ac2f8c4",
              "#8B78F6",
              "#0f225E"
            ];
            //配置图表项,具体参数查询echarts官方文档
            const option = {
              // 图标位置
              grid: {
                top: "10%",
                left: "25%",
                right: "20%",
                bottom: "10%"
              },
              xAxis: {
                show: false
              },
              yAxis: [
                {
                  show: true,
                  data: titlename,
                  inverse: true,
                  axisLine: {
                    show: true
                  },
                  splitLine: {
                    show: true
                  },
                  axisTick: {
                    show: true
                  },
                  axisLabel: {
                    color: "#fff",
                    rich: {
                      lg: {
                        backgroundColor: "#FFF",
                        color: "#fff",
                        borderRadius: 15,
                        align: "center",
                        width: 19,
                        height: 15
                      }
                    }
                  }
                },
                {
                  show: true,
                  inverse: true,
                  data,
                  axisLabel: {
                    textStyle: {
                      fontSize: 12,
                      color: "#fff"
                    }
                  }
                }
              ],
              series: [
                {
                  name: "条",
                  type: "bar",
                  yAxisIndex: 0,
                  data,
                  barCategoryGap: 50,
                  barWidth: 10,
                  itemStyle: {
                    normal: {
                      barBorderRadius: 20,
                      color(params) {
                        const num = myColor.length;
                        return myColor[params.dataIndex % num];
                      }
                    }
                  },
                  label: {
                    normal: {
                      show: false,
                      position: "outside",
                      formatter: "{c}"
                    }
                  }
                },
                {
                  name: "框",
                  type: "bar",
                  yAxisIndex: 1,
                  barCategoryGap: 50,
                  data,
                  barWidth: 15,
                  itemStyle: {
                    normal: {
                      color: "none",
                      borderColor: "#00c1de",
                      borderWidth: 3,
                      barBorderRadius: 15
                    }
                  }
                }
              ]
            };
            chartArray[i].setOption(option);
          }
        }
      });
    }
  }
};
</script>
<style lang="scss" scoped>
.topnItem {
  width: 95%;
  height: 11rem;
  -webkit-tap-highlight-color: transparent;
  user-select: none;
}
.noDataInfo {
  width: 100%;
  height: 100%;
  color: #2ccbef;
  padding: 0% 32% 0 41%;
}
</style>
