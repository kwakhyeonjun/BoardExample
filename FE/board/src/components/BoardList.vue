<template>
  <div class="board">
    <table class="board-table">
      <thead>
        <tr>
          <th>제목</th>
          <th>작성자</th>
          <th>작성일시</th>
          <th>댓글 수</th>
          <th>조회수</th>
          <th>좋아요</th>
        </tr>
      </thead>
      <tbody>
        <template v-if="boardList.length > 0">
          <tr v-for="board in boardList" :key="board.id">
            <router-link :to="`/board/${board.id}`">
              <td>{{ board.title }}</td>
            </router-link>
            <td>{{ board.writer }}</td>
            <td>{{ board.createdDate }}</td>
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
  }
}
</script>
