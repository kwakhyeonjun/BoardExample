<template>
  <div class="board-detail">
    <BoardDetail />
    <Comment />
  </div>
</template>

<script>
import { mapActions } from 'vuex'
import { fetchBoardDetail, fetchComments } from '@/api/boardDetail'

import BoardDetail from '@/components/boardDetail/BoardDetail'
import BoardLike from '@/components/boardDetail/BoardLike'
import Comment from '@/components/boardDetail/Comment'

export default {
  components: {
    BoardDetail,
    BoardLike,
    Comment
  },
  methods: {
    ...mapActions(['setBoardDetail', 'setCommentList']),
    async fetchBoardData (boardId) {
      // 게시글 상세 정보 가져오기
      try {
        const boardDetail = await fetchBoardDetail(boardId)
        this.setBoardDetail(boardDetail)
      } catch (error) {
        console.error('게시글 상세 정보를 가져오는데 실패했습니다:', error)
      }

      // 댓글 목록 가져오기
      try {
        const commentList = await fetchComments(boardId, 1) // 1페이지로 초기화
        this.setCommentList(commentList)
      } catch (error) {
        console.error('댓글 목록을 가져오는데 실패했습니다:', error)
      }
    },
    async handleLoadMoreComments () {
      // 추가 댓글 불러오는 로직 구현
    }
  },
  created () {
    const boardId = this.$route.params.id
    this.fetchBoardData(boardId)
  }
}
</script>
