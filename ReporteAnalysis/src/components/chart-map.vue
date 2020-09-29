<template>
  <div class="panel">
    
    <h3 class="chart-title">{{msg}}分析</h3>
    <p class="content-title">{{msg}}地域分布</p>
    <loadingSign v-if="isLoading" style="top: 41%;left: 47%;"></loadingSign>
    <div id="chartmap" style="width: 100%;height:48%;padding:0 0 0 6%"></div>
    <div class="changeBtn" v-if="isVictim">
      <Button type="info" ghost style="width:100px" @click="changeProfile()">查看攻击者</Button>
    </div>
    <div class="changeBtn" v-else>
      <Button type="info" ghost style="width:100px" @click="changeProfile()">查看受害者</Button>
    </div>
    <h3 class="chart-title">最新{{msg}}详细信息</h3>
    <chart-form class="vicChart" :config="chartConfig"></chart-form>
    <div class="panel-footer"></div>
  </div>
</template>

<script>
import loadingSign from "./loadingSign.vue";
import chartForm from "./chart-form.vue";

export default {
  name: "chartMap",
  components: {
    loadingSign,
    chartForm
  },
  data() {
    return {
      isVictim: true,
      chartConfig: null,
      anotherconfig: this.config,
      attackerOptions: null,
      data: null,
      isLoading: true
    };
  },
  props: {
    msg: String,
    config: Array
  },
  mounted() {
    const myChart = this.$echarts.init(document.getElementById("chartmap"));
    window.addEventListener("resize", () => {
      myChart.resize();
    });
    this.initVicEchart();
  },
  methods: {
    async initVicEchart(data) {
      //开启loading特效
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
      await this.$http.post("/Victim/analysis", params).then(res => {
        if (res.data.code === 200) {
          //状态码200时,关闭loading
          this.isLoading = false;
          this.msg = "受害者";
          this.data = res.data.data;
          //处理获取到的数据,把欧盟转化成具体国家
          this.processData();
          let myseries = [
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
          //初始化echarts
          const myChart = this.$echarts.init(
            document.getElementById("chartmap")
          );
          //准备配置项
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
                color: ["#05CBE9", "#3D85FF"]
              },
              left: 20
            },
            series: myseries
          };
          myChart.setOption(victimOptions);
          this.chartConfig = this.data.latestVictim;
          //为了让表格看着没有空隙,多复制5条进行循环
          this.chartConfig.data = this.chartConfig.data.concat(
            this.chartConfig.data.slice(0, 5)
          );
        }
      });
    },
    async initAttEchart(data) {
      //开启loading特效
      this.isLoading = true;
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
      await this.$http.post("/Attacker/analysis", params).then(res => {
        if (res.data.code === 200) {
          //状态码200时,关闭loading
          this.msg = "攻击者";
          this.isLoading = false;
          this.data = res.data.data;
          //处理获取到的数据,把欧盟转化成具体国家
          this.processData();
          //数据格式转换
          let myseries = [
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
          //初始化echarts
          const myChart = this.$echarts.init(
            document.getElementById("chartmap")
          );
          //准备配置项
          const attackOptions = {
            grid: {
              top: "10%",
              left: "35%",
              right: "20%",
              bottom: "10%"
            },

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
              left: 20
            },
            series: myseries
          };
          myChart.setOption(attackOptions);
          this.chartConfig = this.data.latestAttacker;
          //为了让表格看着没有空隙,多复制5条进行循环
          this.chartConfig.data = this.chartConfig.data.concat(
            this.chartConfig.data.slice(0, 5)
          );
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
          console.log(this.data.maps[0][omIndex]);
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
    },
    changeProfile() {
      //判断当前isVictim是否为true
      if (this.isVictim) {
        this.isVictim = false;
        this.initAttEchart();
      } else {
        this.isVictim = true;
        this.initVicEchart();
      }
    }
  }
};
</script>
<style scoped>
.noDataInfo {
  width: 100%;
  height: 100%;
  color: #2ccbef;
  padding: 0% 32% 0 41%;
}

.vicChart {
  padding: 0 0 0.5rem 0;
  width: 94%;
  position: relative;
  top: -1%;
  left: 3%;
  height: 21%;
}
</style>
