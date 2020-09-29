<template>
  <div style="display:flex;height:100%;" ref="topnBig">
    <div class="topnBox">
      <div class="panel">
        <h3 class="chart-title" style="padding-top:2rem">攻击链TOP5</h3>
        <loadingSign v-if="isLoading" style="top: 34%;left: 23%;"></loadingSign>
        <div id="chartTopn1" class="topn-item"></div>
        <h3 class="chart-title" style="padding-top:1rem;">被攻击设备TOP5</h3>
        <loadingSign v-if="isLoading" style="top: 66%;left: 23%;"></loadingSign>
        <div id="chartTopn2" class="topn-item"></div>
        <div class="panel-footer"></div>
      </div>
    </div>
    <div class="topnBox">
      <div class="panel">
        <h3 class="chart-title" style="padding-top:2rem">模型类型TOP5</h3>
        <loadingSign v-if="isLoading" style="top: 34%;left: 70.5%;"></loadingSign>
        <div id="chartTopn3" class="topn-item"></div>
        <h3 class="chart-title" style="padding-top:1rem">告警子类型TOP5</h3>
        <loadingSign v-if="isLoading" style="top: 66%;left: 70.5%;"></loadingSign>
        <div id="chartTopn4" class="topn-item"></div>
        <div class="panel-footer"></div>
      </div>
    </div>
  </div>
</template>
<script>
import loadingSign from "../components/loadingSign";

export default {
  data() {
    return {
      data: null,
      isLoading: true
    };
  },
  components: {
    loadingSign
  },
  mounted() {
    //设置图表随着容器的宽高自适应
    const myChart = this.$echarts.init(document.getElementById("chartTopn1"));
    const myChart4 = this.$echarts.init(document.getElementById("chartTopn2"));
    const myChart2 = this.$echarts.init(document.getElementById("chartTopn3"));
    const myChart3 = this.$echarts.init(document.getElementById("chartTopn4"));
    window.addEventListener("resize", () => {
      myChart.resize();
      myChart4.resize();
      myChart2.resize();
      myChart3.resize();
    });
    this.initEchart();
  },
  methods: {
    async initEchart(data) {
      //开启loading动效
      this.isLoading = true;
      //设置时间范围
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
      await this.$http.post("/Attacker/topN", params).then(res => {
        if (res.data.code === 200) {
          //状态码200,关闭loading动效
          this.isLoading = false;
          this.data = res.data.data;
          const myChart = this.$echarts.init(
            document.getElementById("chartTopn1")
          );
          const myChart2 = this.$echarts.init(
            document.getElementById("chartTopn2")
          );
          const myChart3 = this.$echarts.init(
            document.getElementById("chartTopn3")
          );
          const myChart4 = this.$echarts.init(
            document.getElementById("chartTopn4")
          );
          //放进array里方便循环
          const chartArray = [myChart, myChart2, myChart3, myChart4];
          for (let i = 0; i < chartArray.length; i++) {
            const { data } = this.data.topN[i];
            const titlename = this.data.topN[i].titleName;
            const { valdata } = this.data.topN[i];
            const myColor = [
              "#2EE7FF",
              "#86FED8",
              "#8ac2f8c4",
              "#8B78F6",
              "#0f225E"
            ];
            //定义图表配置项,详细查询echarts官网
            const option = {
              // 图标位置
              grid: {
                top: "10%",
                left: "20%",
                right: "14%",
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
<style lang="scss">
.topn-item {
  width: 90%;
  height: 39%;
  margin: auto;
}
.topnBox {
  margin: 0 2% 2% 2%;
  height: 96%;
  border-radius: 1rem;
  width: 50%;
}
</style>
