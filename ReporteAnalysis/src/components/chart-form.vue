<template>
  <section>
    <!-- 判断传过来的数据是否为空 -->
    <div class="box"  v-if="config!=null">
      <ul class="header">
        <!-- 循环表头 -->
        <li class="cell" v-for="i in config.header">{{i}}</li>
      </ul>
      <div class="formbody" v-if="config!=null">
        <ul class="list" >
          <!-- 循环表单内容 -->
          <li v-for="(item, index) in config.data" :key="index" class="row">
            <span class="cell" v-for="i in item">{{i }}</span>
          </li>
        </ul>
      </div>
    </div>
  </section>
</template>

<script scoped>
export default {
  data() {
    return {};
  },
  props: {
    config: Object
  }
};
</script>

<style lang="scss">
$cellHeight: 1.7rem;
ul {
  list-style: none;
  margin: 0;
  padding: 0;
  color: white;
  height: 100%;
}
.box {
  width: 100%;
  margin: auto;
  height: 100%;
}
.header {
  display: flex;
  height: 1.8rem;
  background-color: #196eaafa;
}
.formbody {
  height: 5 * $cellHeight;
  overflow: hidden;
  height: 95%;
  background-color: #0a2732;
  color: white;
  li {
    display: flex;
    height: $cellHeight;
  }
}

.cell {
  flex: 1;
  height: $cellHeight;
  box-sizing: border-box;
  text-align: center;
  line-height: $cellHeight;
}
.list {
  animation: scroll 10s linear infinite;
  position: relative;
  li :nth-child(odd) {
    overflow: hidden;
    background-color: #0a2732;
    color: white;
  }
  li :nth-child(even) {
    overflow: hidden;
    background-color: #003b51;
    color: white;
  }
}
@keyframes scroll {
  from {
    top: 0;
  }
  to {
    top: -5 * $cellHeight;
  }
}
</style>
