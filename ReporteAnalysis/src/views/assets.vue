<template>
  <div class="assetContainer" ref="assetBig">
    <div class="assetTContainer">
      <div class="assetItem">
        <div class="panel">
          <h3 class="chart-title" style="padding-top:1.5rem">风险资产分布图</h3>
          <loadingSign v-if="isLoading" style="top: 28%;left: 25%;"></loadingSign>
          <div id="assChart1"></div>
          <div class="panel-footer"></div>
        </div>
      </div>
      <div class="assetItem">
        <div class="panel">
          <h3 class="chart-title" style="padding-top:1.5rem">风险资产TOP5</h3>
          <loadingSign v-if="isLoading" style="top: 28%;left: 69%;"></loadingSign>
          <div id="assTop5"></div>
          <div class="panel-footer"></div>
        </div>
      </div>
    </div>
    <div class="assetBContainer">
      <div class="panel">
        <loadingSign v-if="isLoading" style="top: 70%;left: 45%;"></loadingSign>
        <h3 class="chart-title" style="padding-top:1rem">最新十项风险资产</h3>
        <chart-form style="height:90%;padding:0 3rem 3rem" id="assChart2" :config="assetConfig"></chart-form>
        <div class="panel-footer"></div>
      </div>
    </div>
  </div>
</template>
<script>
import loadingSign from "../components/loadingSign";
import chartForm from "../components/chart-form";

export default {
  components: {
    loadingSign,
    chartForm
  },
  data() {
    return {
      assetConfig: null,
      isLoading: true,
      colors: ["#2EE7FF", "#86FED8", "#0f225E", "#8ac2f8c4"],
      assData: null
    };
  },
  mounted() {
    this.initEchart();
  },
  methods: {
    async initEchart(data) {
      //开启loading动效
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
      await this.$http.post("/RiskAsset/all", params).then(res => {
        //初始化图表
        const numsChart = this.$echarts.init(
          document.getElementById("assChart1")
        );
        const topChart = this.$echarts.init(document.getElementById("assTop5"));
        //loading关闭
        this.isLoading = false;
        this.assData = res.data.data;
        //获取里面的字段
        const titlename = this.assData.riskAssetVo3.titleName;
        const data = this.assData.riskAssetVo3.data;
        const valdata = this.assData.riskAssetVo3.valData;
        const myColor = [
          "#2EE7FF",
          "#86FED8",
          "#8ac2f8c4",
          "#8B78F6",
          "#0f225E"
        ];
        //配置图表项,具体查询echarts
        const topOption = {
          // 图标位置
          grid: {
            top: "0%",
            left: "35%",
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
        //配置图表项,具体查询echarts
        const numsOption = {
          color: ["#2EE7FF", "#86FED8", "#0f225E", "#8ac2f8c4"],
          tooltip: {
            trigger: "item",
            formatter: "{a} <br/>{b}: {c} ({d}%)"
          },
          legend: {
            orient: "vertical",
            left: 80,
            top: 10,
            data: ["低风险", "已失陷", "高风险"],
            textStyle: {
              color: "white"
            }
          },
          series: [
            {
              name: "资产分布",
              type: "pie",
              radius: ["60%", "80%"],
              top: 0,
              left: 10,
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
        };
        numsChart.setOption(numsOption);
        // typeChart.setOption(numsOption);
        topChart.setOption(topOption);
        this.assetConfig = this.assData.riskAssetVo1;
        this.assetConfig.data = this.assetConfig.data.concat(
          this.assetConfig.data.slice(0, 5)
        );
      });
    }
  }
};
</script>
<style scoped>
.assetContainer {
  padding: 0 1.4rem;
  height: 100%;
  display: flex;
  border-radius: 1.076923rem;
  flex-direction: column;
}
.assetTContainer {
  display: flex;
  width: 100%;
  height: 40%;
}
.assetBContainer {
  height: 50%;
  margin: 1rem 0 0 0;
}
.assetItem {
  margin: 0 2rem 0 3rem;
  width: 100%;
}
#assChart1 {
  width: 100%;
  height: 74%;
}
#assTop5 {
  width: 100%;
  height: 74%;
}
</style>
