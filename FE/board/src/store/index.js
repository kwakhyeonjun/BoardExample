// src/store/index.js
import Vue from 'vue'
import Vuex from 'vuex'
import { fetchBoards, fetchBoardAndCommentCount, searchBoards } from '../api'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    boardList: [], // 게시글 목록을 저장할 상태
    boardCount: 0, // 총 게시글 수를 저장할 상태
    commentCount: 0, // 총 댓글 수를 저장할 상태
    searchOption: 'TITLE', // 검색 대상을 저장할 상태 (기본값: '제목')
    searchKeyword: '', // 검색어를 저장할 상태
    searchResult: [] // 검색 결과를 저장할 상태
  },
  mutations: {
    setBoardList (state, boardList) {
      state.boardList = boardList
    },
    setBoardCount (state, boardCount) {
      state.boardCount = boardCount
    },
    setCommentCount (state, commentCount) {
      state.commentCount = commentCount
    },
    setSearchOption (state, searchOption) {
      state.searchOption = searchOption
    }
  },
  actions: {
    async fetchBoardList ({ commit }) {
      try {
        const boardList = await fetchBoards() // 게시글 목록을 가져오는 함수 사용
        commit('setBoardList', boardList)
      } catch (error) {
        console.error('Error fetching board list:', error)
        commit('setBoardList', [])
      }
    },
    async fetchBoardAndCommentCount ({ commit }) {
      try {
        const data = await fetchBoardAndCommentCount() // 게시글과 댓글 수를 가져오는 함수 사용
        commit('setBoardCount', data.boardCount)
        commit('setCommentCount', data.commentCount)
      } catch (error) {
        console.error('Error fetching board and comment count:', error)
        commit('setBoardCount', 0)
        commit('setCommentCount', 0)
      }
    },
    async searchBoards ({ state, commit }) {
      try {
        const searchOption = state.searchOption
        const searchKeyword = state.searchKeyword

        if (!searchKeyword.trim()) {
          // 검색어가 비어있는 경우 전체 게시글 목록을 보여줌
          commit('setSearchResult', [])
          return
        }

        const searchResult = await searchBoards(searchOption, searchKeyword)
        commit('setSearchResult', searchResult)
      } catch (error) {
        console.error('Error searching boards:', error)
        commit('setSearchResult', [])
      }
    }
  }
})
