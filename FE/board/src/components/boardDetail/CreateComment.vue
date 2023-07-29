<template>
  <div>
    <!-- 댓글 작성 폼 -->
    <form @submit.prevent="handleSubmit">
      <div>
        <label for="writer">작성자</label>
        <input v-model="writer" type="text" id="writer" required />
      </div>
      <div>
        <label for="password">비밀번호</label>
        <input v-model="password" type="password" id="password" required />
      </div>
      <div>
        <label for="content">댓글 내용</label>
        <textarea v-model="commentContent" id="content" placeholder="댓글을 입력하세요" required></textarea>
      </div>
      <button type="submit">작성</button>
    </form>
  </div>
</template>

<script>
import { createComment } from '@/api/boardDetail'
import { mapActions } from 'vuex'

export default {
  data () {
    return {
      boardId: '',
      writer: '',
      password: '',
      commentContent: ''
    }
  },
  methods: {
    ...mapActions(['createComment']),
    async handleSubmit () {
      try {
        const commentData = {
          content: this.commentContent,
          boardId: this.boardId,
          writer: this.writer,
          password: this.password
        }
        console.log(commentData.boardId)
        const newComment = await createComment(commentData)

        this.createComment(newComment)
        this.commentContent = ''
        this.writer = '' // 작성자 입력란 초기화
        this.password = '' // 비밀번호 입력란 초기화
      } catch (error) {
        console.error('댓글 작성에 실패했습니다:', error)
      }
    }
  },
  created () {
    this.boardId = this.$route.params.id
  }
}
</script>
