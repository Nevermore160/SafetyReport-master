import Vue from 'vue';
import ViewUI from 'view-design';
import echarts from 'echarts';
import dataV from '@jiaminghi/data-view';
import App from './App.vue';
import router from './router';
import store from './store';
import 'view-design/dist/styles/iview.css';
import './assets/style.scss';
import './plugins/world';

// import http from './plugins/axios';
// Vue.prototype.$http = http;
Vue.prototype.$echarts = echarts;
Vue.config.productionTip = false;

import axios from 'axios'
axios.defaults.baseURL ="http://47.115.43.39:8080/";
Vue.prototype.$http = axios

Vue.use(ViewUI);
Vue.use(dataV);
Vue.config.warnHandler = function (msg) {
  if (!msg.includes('Avoid mutating a prop directly since the value will be overwritten whenever the parent component re-renders.')) { // uniApp bug: https://ask.dcloud.net.cn/question/71966
    return console.warn && console.warn(msg);
  }
};
new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount('#app');
