<template>
  <div class="attackContainer" ref="attBig">
    <div class="attackTop">
      <div class="panel" style="width:55%;margin:0 0 0 1rem">
        <h3 class="chart-title">攻击者分布图</h3>
        <p class="content-title">攻击者地域分布</p>
        <loadingSign v-if="isLoading" style="top: 40%;left: 28%;"></loadingSign>
        <div id="attackmap-big" style="height:90%;width:100%;padding:0 5rem 1rem;"></div>
        <div class="panel-footer"></div>
      </div>
      <div class="panel" style="width:40%;margin:0 0 0 2rem">
        <h3 class="chart-title">攻击者TOP5</h3>
        <loadingSign v-if="isLoading" style="top: 40%;left: 75%;"></loadingSign>
        <div id="attack-top" class="attackTop-item"></div>
        <div class="panel-footer"></div>
      </div>
    </div>
    <div class="attackBottom">
      <h3 class="chart-title" style="padding: 0.5rem 1.3rem">最新攻击者列表</h3>
      <loadingSign v-if="isLoading" style="top:77%;left: 44%;"></loadingSign>
      <chart-form :config="chartConfig" id="vicChart"></chart-form>
    </div>
  </div>
</template>
<script>
import loadingSign from "../components/loadingSign";
import chartForm from "../components/chart-form.vue";

export default {
  components: {
    loadingSign,
    chartForm
  },
  data() {
    return {
      data: null,
      attackData: null,
      chartConfig: null,
      isLoading: true
    };
  },
  mounted() {
    const myChart = this.$echarts.init(
      document.getElementById("attackmap-big")
    );
    const myChart1 = this.$echarts.init(document.getElementById("attack-top"));
    window.addEventListener("resize", () => {
      myChart.resize();
      myChart1.resize();
    });
    this.initEchart();
  },
  methods: {
    async initEchart(data) {
      //开启loading
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
      await this.$http.post("/Attacker/analysis", params).then(res => {
        if (res.data.code === 200) {
          //状态码200,关闭loading动效
          this.isLoading = false;
          this.data = res.data.data;
          //处理数据
          this.processData();
          //处理数据
          const myseries = [
            {
              name: "攻击热度",
              type: "map",
              mapType: "world",
              label: {
                show: false
              },
              data: this.data.maps[0]
            },
            {
              name: "主要攻击链类型",
              type: "map",
              mapType: "world",
              label: {
                show: false
              },
              data: this.data.maps[1]
            }
          ];
          //初始化图表
          const mapChart = this.$echarts.init(
            document.getElementById("attackmap-big")
          );
          //配置图表项
          const attackerOptions = {
            tooltip: {
              trigger: "item",
              textStyle: {
                color: "#fff"
              },
              formatter(params) {
                let res = `${params.name}<br/>`;
                for (let i = 0; i < myseries.length; i += 1) {
                  for (let j = 0; j < myseries[i].data.length; j += 1) {
                    if (myseries[i].data[j].name === params.name) {
                      res += `${myseries[i].name} : ${myseries[i].data[j].value}</br>`;
                    }
                  }
                }
                return res;
              }
            },

            visualMap: {
              min: 0,
              max: 1100000,
              realtime: false,
              calculable: true,
              inRange: {
                color: ["#15cbec", "#3D85FF"]
              },
              left: -10,
              bottom: 10
            },
            series: myseries
          };
          mapChart.setOption(attackerOptions);
          this.chartConfig = this.data.latestAttacker;
          this.chartConfig.data = this.chartConfig.data.concat(
            this.chartConfig.data.slice(0, 5)
          );
          const attacktop = this.$echarts.init(
            document.getElementById("attack-top")
          );
          attacktop.setOption({
            color: ["#3398DB"],
            tooltip: {
              trigger: "axis",
              axisPointer: {
                type: "shadow"
              }
            },
            grid: {
              left: "3%",
              right: "4%",
              bottom: "13%",
              containLabel: true
            },
            xAxis: [
              {
                type: "category",
                data: this.data.topAttacker.xaxis,
                axisTick: {
                  alignWithLabel: true
                },
                lineStyle: {
                  type: "solid",
                  color: "#fff", // 左边线的颜色
                  width: "2" // 坐标线的宽度
                },
                axisLabel: {
                  textStyle: {
                    color: "#fff" // 坐标值得具体的颜色
                  }
                }
              }
            ],
            yAxis: [
              {
                type: "value",
                axisLabel: {
                  textStyle: {
                    color: "#fff" // 坐标值得具体的颜色
                  }
                }
              }
            ],
            series: [
              {
                name: "发起攻击次数",
                type: "bar",
                barWidth: "60%",
                label: {
                  show: true,
                  position: "top"
                },
                data: this.data.topAttacker.yaxis
              }
            ]
          });
        }
      });
    },
    processData() {
      if (this.data.maps[0][0] != undefined) {
        const v = 0;
        const omIndex = this.data.maps[0].findIndex(
          element => element.name === "欧盟"
        );
        const Europe = [
          "Austria",
          "Belgium",
          "Bulgaria",
          "Croatia",
          "Cyprus",
          "Czech Rep",
          "Denmark",
          "Estonia",
          "Finland",
          "Greece",
          "Hungary",
          "Ireland",
          "Latvia",
          "United Kingdom",
          "Luxembourg",
          "Republic of Malta",
          "Nederland",
          "Portugal",
          "Slovakia",
          "Slovenia"
        ];
        if (omIndex != -1) {
          this.data.maps[0].splice(omIndex, 1, {
            name: Europe[0],
            value: this.data.maps[0][omIndex].value
          });
          for (let i = 1; i < Europe.length; i++) {
            this.data.maps[0].splice(omIndex, 0, {
              name: Europe[i],
              value: this.data.maps[0][omIndex].value
            });
          }
          this.data.maps[1].splice(omIndex, 1, {
            name: Europe[0],
            value: this.data.maps[1][omIndex].value
          });
          for (let i = 1; i < Europe.length; i++) {
            this.data.maps[1].splice(omIndex, 0, {
              name: Europe[i],
              value: this.data.maps[1][omIndex].value
            });
          }
        }
      }
    }
  }
};
</script>
<style scoped>
.attackmap-big {
  width: 100%;
}
.attackTop {
  display: flex;
  width: 100%;
  height: 60%;
  margin: 0.5rem 0 -0.5rem 0;
}
.attackBottom {
  margin: 0rem 3.5rem 0.3rem 2.5%;
  border-radius: 1.07rem;
  width: 100%;
  height: 32%;
}
.attackTop-item {
  width: 100%;
  height: 90%;
  padding: 0 3rem 3rem;
}
.attackContainer {
  margin: -0.5rem;
  height: 100%;
}
#vicChart {
  height: 86%;
  position: relative;
  padding: 0 4rem 3rem 0;
  z-index: 10;
}
</style>
