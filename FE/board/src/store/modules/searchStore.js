import Vue from 'vue'
import Vuex from 'vuex'
import { searchBoards } from '../../api/mainBoard'

Vue.use(Vuex)

const searchStore = {
  state: {
    searchOption: 'title', // 검색 대상을 저장할 상태 (기본값: '제목')
    searchKeyword: '', // 검색어를 저장할 상태
    searchResult: [] // 검색 결과를 저장할 상태
  },
  mutations: {
    SET_SEARCH_OPTION (state, searchOption) {
      state.searchOption = searchOption
    },
    SET_SEARCH_KEYWORD (state, searchKeyword) {
      state.searchKeyword = searchKeyword
    },
    SET_SEARCH_RESULT (state, searchKeyword) {
      state.searchKeyword = searchKeyword
    }
  },
  actions: {
    async searchBoards ({ state, commit }) {
      try {
        const searchOption = state.searchOption
        const searchKeyword = state.searchKeyword

        if (!searchKeyword.trim()) {
          commit('setSearchResult', [])
          return
        }

        const searchResult = await searchBoards(searchOption, searchKeyword)
        commit('SET_SEARCH_RESULT', searchResult)
      } catch (error) {
        console.error('잘못된 검색입니다: ', error)
        commit('SET_SEARCH_RESULT', [])
      }
    }
  }
}

export default searchStore
