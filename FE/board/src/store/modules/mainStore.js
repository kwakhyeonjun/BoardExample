import { fetchBoardList, fetchBoardAndCommentCount } from '@/api/mainBoard.js'

const mainStore = {
  state: {
    boardList: [], // 게시글 목록을 저장할 상태
    boardCount: 0, // 총 게시글 수를 저장할 상태
    commentCount: 0 // 총 댓글 수를 저장할 상태
  },
  mutations: {
    SET_BOARD_LIST (state, boardList) {
      state.boardList = boardList
    },
    SET_TOTAL_BOARD_COUNT (state, boardCount) {
      state.boardCount = boardCount
    },
    SET_TOTAL_COMMENT_COUNT (state, commentCount) {
      state.commentCount = commentCount
    }
  },
  actions: {
    async fetchBoardList ({ commit }) {
      try {
        const response = await fetchBoardList()
        commit('SET_BOARD_LIST', response.data)
      } catch (error) {
        console.error('게시글 목록을 불러오지 못했습니다:', error)
        commit('SET_BOARD_LIST', [])
      }
    },
    async fetchBoardAndCommentCount ({ commit }) {
      try {
        const data = await fetchBoardAndCommentCount()
        commit('SET_TOTAL_BOARD_COUNT', data.boardCount)
        commit('SET_TOTAL_COMMENT_COUNT', data.commentCount)
      } catch (error) {
        console.error('갯수를 불러오지 못했습니다:', error)
        commit('SET_TOTAL_BOARD_COUNT', 0)
        commit('SET_TOTAL_COMMENT_COUNT', 0)
      }
    }
  },
  getters: {
    getBoardCount: state => state.boardCount,
    getCommentCount: state => state.commentCount
  }
}

export default mainStore
