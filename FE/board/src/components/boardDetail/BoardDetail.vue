<template>
  <div class="board-detail">
    <!-- 게시물 상세 정보 표시 -->
    <h1>{{ boardDetail.title }}</h1>
    <p>{{ boardDetail.writer }}</p>
    <p>{{ boardDetail.createdDate }}</p>
    <p>{{ boardDetail.content }}</p>
    <div>
      <span>해시태그:</span>
      <span v-for="hashtag in boardDetail.hashtagList" :key="hashtag">
        {{ hashtag }}
      </span>
    </div>
    <div>조회수: {{ boardDetail.viewCount }}</div>
    <div>좋아요 수: {{ boardDetail.likeCount }}</div>
    <!-- 좋아요 기능 -->
    <BoardLike />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

import BoardLike from './BoardLike'
import Comment from './Comment'

export default {
  data () {
    return {
      boardId: ''
    }
  },
  components: {
    BoardLike,
    Comment
  },
  computed: {
    ...mapGetters(['getBoardDetail', 'getIsLike']),
    boardDetail () {
      return this.getBoardDetail
    },
    isLike () {
      return this.getIsLike
    }
  }
}
</script>
