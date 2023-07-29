const detailStore = {
  state: {
    boardId: '',
    boardDetail: {},
    commentList: [],
    isLike: false
  },
  mutations: {
    SET_BOARD_ID (state, boardId) {
      state.boardId = boardId
    },
    SET_BOARD_DETAIL (state, boardDetail) {
      state.boardDetail = boardDetail
    },
    SET_COMMENT_LIST (state, commentList) {
      state.commentList = commentList
    },
    SET_IS_LIKE (state) {
      state.isLike = !state.isLike
    },
    SET_LIKE_COUNT (state) {
      state.boardDetail.likeCount++
    },
    ADD_COMMENT (state, newComment) {
      state.commentList.push(newComment)
    }
  },
  actions: {
    setBoardId ({ commit }, boardId) {
      commit('SET_BOARD_ID', boardId)
    },
    setBoardDetail ({ commit }, boardDetail) {
      commit('SET_BOARD_DETAIL', boardDetail)
    },
    setCommentList ({ commit }, commentList) {
      commit('SET_COMMENT_LIST', commentList)
    },
    setLikeCount ({ commit }) {
      commit('SET_LIKE_COUNT')
    },
    setIsLike ({ commit }) {
      commit('SET_IS_LIKE')
    },
    createComment ({ commit }, newComment) {
      commit('ADD_COMMENT', newComment)
    }
  },
  getters: {
    getBoardId: (state) => state.boardId,
    getBoardDetail: (state) => state.boardDetail,
    getComments: (state) => state.commentList,
    getIsLike: (state) => state.isLike,
    getCommentList: (state) => state.commentList
  }
}

export default detailStore
