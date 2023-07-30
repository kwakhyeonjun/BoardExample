<template>
  <div class="board-detail">
    <div class="first-line">
      <div class="title">{{ boardDetail.title }}</div>
      <div class="writer">{{ boardDetail.writer }}</div>
      <div class="created-date">{{ formatDate(boardDetail.createdDate) }}</div>
    </div>

    <p class="content">{{ boardDetail.content }}</p>
    <div class="hashtags">
      <span>해시태그:</span>
      <span v-for="hashtag in boardDetail.hashtagList" :key="hashtag">
        {{ hashtag }}
      </span>
    </div>
    <div class="info">
      <span>조회수: {{ boardDetail.viewCount }}</span>
      <span>좋아요 수: {{ boardDetail.likeCount }}</span>
    </div>
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
  },
  methods: {
    formatDate (date) {
      const options = {
        year: '2-digit',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
        hour12: false
      }
      return new Date(date).toLocaleString('ko-KR', options).replace(',', '')
    }
  }
}
</script>

<style>
body {
  margin: 0;
  font-family: Arial, sans-serif;
}

.board-detail {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.first-line {
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 20px;
}

.first-line .title {
  flex: 2;
}

.first-line .writer {
  flex: 1;
  margin-left: 20px;
}

.first-line .created-date {
  flex: 1;
  text-align: right;
  color: #888;
}

.board-detail h1 {
  font-size: 28px;
  font-weight: bold;
  margin-bottom: 10px;
}

.board-detail .author {
  font-size: 14px;
  color: #666;
}

.board-detail .created-date {
  font-size: 12px;
  color: #888;
  margin-bottom: 15px;
}

.board-detail .content {
  font-size: 16px;
  line-height: 1.6;
  margin-bottom: 20px;
}

.board-detail .separator {
  margin: 30px 0;
  border-bottom: 1px solid #ccc;
}

.board-detail .hashtags {
  margin-bottom: 20px;
}

.board-detail .hashtags span {
  margin-right: 10px;
  color: #888;
}

.board-detail .info {
  display: flex;
  justify-content: space-between;
  font-size: 14px;
  color: #888;
}
</style>
