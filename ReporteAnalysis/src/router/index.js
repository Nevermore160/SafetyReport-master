import Vue from 'vue';
import VueRouter from 'vue-router';
import ViewUI from 'view-design';

Vue.use(ViewUI);
Vue.use(VueRouter);

const routes = [

  {
    path: '/',
    name: 'Home',
    component: () => import('../views/home.vue'),
  },
  {
    path: '/timetrend',
    name: 'Timetrend',
    component: () => import('../views/timeTrend.vue'),
  },
  {
    path: '/attackers',
    name: 'Attackers',
    component: () => import('../views/attackers.vue'),
  },
  {
    path: '/victims',
    name: 'victims',
    component: () => import('../views/victims.vue'),
  },
  {
    path: '/topn',
    name: 'TopN',
    component: () => import('../views/topn.vue'),
  },
  {
    path: '/assets',
    name: 'Assets',
    component: () => import('../views/assets.vue'),
  },
];

const router = new VueRouter({
  mode: 'hash',
  base: process.env.BASE_URL,
  routes,
});
router.beforeEach((to, from, next) => {
  ViewUI.LoadingBar.start();
  next();
});

router.afterEach((route) => {
  ViewUI.LoadingBar.finish();
});
export default router;
const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch((err) => err);
};
