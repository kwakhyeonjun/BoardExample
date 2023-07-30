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

<style>
.count-bar {
  background-color: #03c75a;
  padding: 10px;
  color: white;
  display: flex;
  justify-content: flex-end;
}

.count-item {
  display: flex;
  align-items: center;
}

.count-label {
  margin-right: 3px;
}

.count-value {
  font-weight: bold;
  margin-right: 10px;
}
</style>
