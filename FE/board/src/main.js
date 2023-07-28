import Vue from 'vue'
import App from './App.vue'
import store from './store'
import router from './router'

// 검색 대상에 따라 표시할 텍스트를 필터로 정의
Vue.filter('searchTargetText', (value) => {
  const targetTextMap = {
    TITLE: 'Title',
    CONTENT: 'Content',
    WRITER: 'Writer',
    HASHTAG: 'Hashtag'
  }
  return targetTextMap[value] || ''
})

new Vue({
  store,
  router,
  render: (h) => h(App)
}).$mount('#app')
