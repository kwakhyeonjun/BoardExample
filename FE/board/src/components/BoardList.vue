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
        <tr v-for="board in boardList" :key="board.id">
          <td>{{ board.title }}</td>
          <td>{{ board.writer }}</td>
          <td>{{ board.createdDate }}</td>
          <td>{{ board.commentCount }}</td>
          <td>{{ board.viewCount }}</td>
          <td>{{ board.likeCount }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  props: {
    boardList: Array
  }
}
</script>
