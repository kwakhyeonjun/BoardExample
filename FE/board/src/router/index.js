import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '@/views/Main.vue'
import CreateBoard from '@/views/CreateBoard.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Main',
    component: Main
  },
  {
    path: '/create',
    name: 'CreateBoard',
    component: CreateBoard
  }
]

const router = new VueRouter({
  mode: 'history',
  routes
})

export default router
