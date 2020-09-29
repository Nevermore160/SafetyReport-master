<template>
  <div class="victimContainer" ref="vicBig">
    <div class="victimTop">
      <div class="panel" style="width:55%;margin:0 0 0 1rem">
        <h3 class="chart-title">受害者分布图</h3>
        <p class="content-title">攻击者地域分布</p>
        <loadingSign v-if="isLoading" style="top: 40%;left: 28%;"></loadingSign>
        <div id="victimmap-big" style="height:90%;width:100%;padding:0 5rem 1rem;"></div>
        <div class="panel-footer"></div>
      </div>
      <div class="panel" style="width:40%;margin:0 0 0 2rem">
        <h3 class="chart-title">受害者TOP5</h3>
        <loadingSign v-if="isLoading" style="top: 40%;left: 75%;"></loadingSign>
        <div id="victim-top" class="victimTop-item"></div>
        <div class="panel-footer"></div>
      </div>
    </div>
    <div class="victimBottom">
      <h3 class="chart-title" style="padding: 0.5rem 1.3rem">最新受害者列表</h3>
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
      victimData: null,
      chartConfig: null,
      isLoading: true
    };
  },
  mounted() {
    //初始化两张表格
    const myChart = this.$echarts.init(
      document.getElementById("victimmap-big")
    );
    const myChart1 = this.$echarts.init(document.getElementById("victim-top"));
    //当屏幕变化时监听，自动触发resize事件
    window.addEventListener("resize", () => {
      myChart.resize();
      myChart1.resize();
    });
    this.initEchart();
  },
  methods: {
    async initEchart(data) {
      //当请求发送时，启动loading动画
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
      //发送请求
      await this.$http.post("/Victim/analysis", params).then(res => {
        //获取数据
        this.data = res.data.data;
        //关闭loading动画
        this.isLoading = false;
        //数据格式
        const myseries = [
          {
            name: "受害热度",
            type: "map",
            mapType: "world",
            label: {
              show: false
            },
            data: this.data.maps[0]
          },
          {
            name: "主要受害告警类型",
            type: "map",
            mapType: "world",
            label: {
              show: false
            },
            data: this.data.maps[1]
          }
        ];
        this.processData();
        const mapChart = this.$echarts.init(
          document.getElementById("victimmap-big")
        );
        const victimOptions = {
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
        mapChart.setOption(victimOptions);
        this.chartConfig = this.data.latestVictim;
        this.chartConfig.rowNum = 3;
        this.chartConfig.data = this.chartConfig.data.concat(
          this.chartConfig.data.slice(0, 5)
        );
        const victimtop = this.$echarts.init(
          document.getElementById("victim-top")
        );
        victimtop.setOption({
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
              data: this.data.topVictim.xaxis,
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
              name: "受害次数",
              type: "bar",
              barWidth: "60%",
              label: {
                show: true,
                position: "top"
              },
              data: this.data.topVictim.yaxis
            }
          ]
        });
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
.victimmap-big {
  width: 100%;
}
.victimTop {
  display: flex;
  width: 100%;
  height: 60%;
  margin: 0.5rem 0 -0.5rem 0;
}
.victimBottom {
  margin: 0rem 3.5rem 0.3rem 2.5%;
  border-radius: 1.07rem;
  width: 100%;
  height: 32%;
}
.victimTop-item {
  width: 100%;
  height: 90%;
  padding: 0 3rem 3rem;
}
.victimContainer {
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
