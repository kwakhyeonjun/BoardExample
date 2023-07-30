<template>
  <div class="board">
    <table class="board-table">
      <thead>
        <tr>
          <th>작성일시</th>
          <th>제목</th>
          <th>작성자</th>
          <th>댓글 수</th>
          <th>조회수</th>
          <th>좋아요</th>
        </tr>
      </thead>
      <tbody>
        <template v-if="boardList.length > 0">
          <tr v-for="board in boardList" :key="board.id">
            <td>{{ formatDate(board.createdDate) }}</td>
            <td>
              <router-link :to="`/board/${board.id}`">
                <span>{{ board.title }}</span>
                <span v-if="isNewPost(board)" class="new-tag">[New]</span>
              </router-link>
            </td>
            <td>{{ board.writer }}</td>
            <td>{{ board.commentCount }}</td>
            <td>{{ board.viewCount }}</td>
            <td>{{ board.likeCount }}</td>
          </tr>
        </template>
        <template v-else>
          <tr>
            <td colspan="6">검색 결과가 없습니다.</td>
          </tr>
        </template>
      </tbody>
    </table>
  </div>
</template>
<script>
import { mapGetters } from 'vuex'

export default {
  computed: {
    ...mapGetters(['getBoardList', 'getSearchResult']),
    searchResult () {
      return this.getSearchResult
    },
    boardList () {
      if (this.getSearchResult.length > 0) return this.getSearchResult
      return this.getBoardList
    }
  },
  methods: {
    isNewPost (board) {
      const currentDate = new Date()
      const postDate = new Date(board.createdDate)
      const timeDifference = currentDate.getTime() - postDate.getTime()
      const threeDaysInMillis = 3 * 24 * 60 * 60 * 1000
      return timeDifference < threeDaysInMillis
    },
    formatDate (date) {
      const options = { year: '2-digit', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit', hour12: false }
      return new Date(date).toLocaleString('ko-KR', options).replace(',', '')
    }
  }
}
</script>

<style>
.board {
  margin-top: 20px;
}

.board-table {
  width: 100%;
  border-collapse: collapse;
}

.board-table th,
.board-table td {
  border: none;
  padding: 10px;
  text-align: center;
}

.board-table th {
  background-color: #f0f0f0;
  font-weight: bold;
}

.board-table a {
  text-decoration: none;
  color: #333;
  display: inline-block;
  position: relative;
}

.board-table a:hover {
  text-decoration: underline;
}

.new-tag {
  color: #ff5722;
  font-size: 12px;
  font-weight: bold;
  margin-left: 5px;
}

.board-table td:nth-child(1) {
  min-width: 120px;
}

.board-table td:nth-child(2) {
  text-align: left;
}

.board-table td:nth-child(n+3) {
  min-width: 80px;
}
</style>
