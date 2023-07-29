import Vue from 'vue'
import Vuex from 'vuex'
import mainStore from '@/store/modules/mainStore'
import searchStore from '@/store/modules/searchStore'
import detailStore from '@/store/modules/detailStore'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    mainStore,
    searchStore,
    detailStore
  }
})

export default store
