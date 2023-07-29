<template>
  <div>
    <h2>댓글</h2>
    <ul>
      <li v-for="comment in commentList" :key="comment.id">
        <p>{{ comment.content }}</p>
        <p>{{ comment.writer }}</p>
        <p>{{ comment.createdDate }}</p>
      </li>
    </ul>
    <button @click="handleLoadMore">더보기</button>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  computed: {
    ...mapGetters(['getComments']),
    commentList () {
      return this.getComments
    }
  },
  methods: {
    handleLoadMore () {
      // 추가 댓글 불러오는 로직 구현
    }
  }
}
</script>
