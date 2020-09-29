<template>
  <div class="homeContainer">
    <div class="itemLeft">
      <chartTime ref="time" :msg="title1" style="height:50%"></chartTime>
      <chartAsset
        ref="asset"
        :msg="title2"
        :config="assetConfig"
        :data="assetData"
        style="height:50%"
      ></chartAsset>
    </div>
    <div class="itemMid">
      <chartMap ref="map" class="map" :msg="title3" :config="mapConfig"></chartMap>
    </div>
    <div class="itemRight">
      <chartTopN ref="topn" class="topn1" :msg="title4" :data="topnData"></chartTopN>
      <btnSuggest ref="suggest" class="btnSuggest" :msg="title5"></btnSuggest>
    </div>
  </div>
</template>
<script>
import chartTime from "../components/chart-time.vue";
import chartAsset from "../components/chart-asset.vue";
import chartMap from "../components/chart-map.vue";
import chartTopN from "../components/chart-topn.vue";
import btnSuggest from "../components/btn-suggest.vue";

export default {
  data() {
    return {
      color: "#091059",
      time: "",
      title1: "数据时序分布趋势图",
      title2: "风险资产分布图",
      title3: "受害者",
      title4: "TOPN风险排行",
      title5: "查看说明及建议",
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
      timeData: null,
      assetConfig: null,
      assetData: null,
      mapData: null,
      mapConfig: null,
      topnData: null,
      data_IsImage: true,
      isFullScreen: false
    };
  },
  created() {},
  components: {
    chartTime,
    chartAsset,
    chartMap,
    chartTopN,
    btnSuggest
  },
  methods: {}
};
</script>
<style scoped>
.map {
  height: 100%;
}
.topn1 {
  width: 100%;
  height: 89%;
}
.homeContainer {
  width: 100%;
  flex: 1;
  height: 0;
  display: flex;
}
.itemLeft {
  flex: 1;
}
.itemMid {
  flex: 2;
  padding: 0 1.3rem;
}
.itemRight {
  flex: 1;
}
</style>
