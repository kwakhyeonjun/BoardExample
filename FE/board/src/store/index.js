import Vue from 'vue'
import Vuex from 'vuex'
import mainStore from '@/store/modules/mainStore'
import searchStore from '@/store/modules/searchStore'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    mainStore, searchStore
  }
})

export default store
