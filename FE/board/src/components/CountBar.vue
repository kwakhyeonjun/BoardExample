<template>
  <div class="count-bar">
    <div class="count-item">
      <span class="count-label">게시글 수:</span>
      <span class="count-value">{{ boardCount }}</span>
      <span class="count-label">댓글 수:</span>
      <span class="count-value">{{ commentCount }}</span>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  computed: {
    ...mapGetters(['getBoardCount', 'getCommentCount']),
    boardCount () {
      return this.getBoardCount
    },
    commentCount () {
      return this.getCommentCount
    }
  }
}
</script>
