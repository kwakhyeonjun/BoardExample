<template>
  <div class="board-detail">
    <h1>{{ board.title }}</h1>
    <p>작성자: {{ board.writer }}</p>
    <p>조회수: {{ board.viewCount }}</p>
    <p>좋아요 수: {{ likeCount }}</p>
    <p>내용: {{ board.content }}</p>
    <p>해시태그: {{ board.hashtags.join(', ') }}</p>

    <button @click="handleLike" :disabled="isLiked">좋아요</button>

    <h2>댓글</h2>
    <ul>
      <li v-for="comment in comments" :key="comment.id">{{ comment.text }}</li>
    </ul>

    <button v-if="showMoreButton" @click="loadMoreComments">더보기</button>
  </div>
</template>

<script>
import { fetchBoardDetail, likeBoard, fetchComments, fetchLikeCount } from '@/api/boardDetail.js'

export default {
  data () {
    return {
      board: {},
      likeCount: 0,
      isLiked: false,
      comments: [],
      showMoreButton: false,
      currentPage: 1,
      pageSize: 5
    }
  },
  methods: {
    async fetchBoardData () {
      const boardId = this.$route.params.id
      this.board = await fetchBoardDetail(boardId)
    },
    async fetchLikeCount () {
      const boardId = this.$route.params.id
      this.likeCount = await fetchLikeCount(boardId)
    },
    async handleLike () {
      if (!this.isLiked) {
        try {
          await likeBoard(this.boardId)
          // 좋아요 요청이 성공한 후에 일정 시간 후에 재클릭 가능하도록 설정
          setTimeout(() => {
            this.isLiked = false
          }, 1000) // 1초로 설정, 원하는 시간으로 변경 가능
        } catch (error) {
          console.error('좋아요 요청에 실패했습니다:', error)
          this.likeButtonDisabled = false
        }
      }
    },
    async fetchComments () {
      const boardId = this.$route.params.id
      this.comments = await fetchComments(boardId, this.currentPage, this.pageSize)
      this.showMoreButton = this.comments.length === this.pageSize
    },
    async loadMoreComments () {
      this.currentPage++
      const boardId = this.$route.params.id
      const newComments = await fetchComments(boardId, this.currentPage, this.pageSize)
      this.comments = [...this.comments, ...newComments]
      this.showMoreButton = newComments.length === this.pageSize
    }
  },
  async mounted () {
    await this.fetchBoardData()
    await this.fetchLikeCount()
    await this.fetchComments()
  }
}
</script>

<style>
.board-detail {
  max-width: 500px;
  margin: 0 auto;
}

.board-detail h1 {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 10px;
}

.board-detail p {
  margin-bottom: 5px;
}

.board-detail button {
  padding: 8px 16px;
  background-color: #007bff;
  color: #fff;
  border: none;
  cursor: pointer;
  margin-top: 10px;
}

.board-detail ul {
  list-style: none;
  padding: 0;
}

.board-detail li {
  margin-bottom: 5px;
}
</style>
