<template>
  <div>
    <h2>댓글</h2>
    <ul>
      <li v-for="comment in commentList" :key="comment.id">
        <template v-if="!comment.deleted">
          {{ comment.content }}
          <p> </p>
          <input
            v-if="callDelete"
            v-model="comment.password"
            type="password"
            placeholder="비밀번호를 입력하세요"
          />
          <p>{{ comment.writer }}</p>
          <p>{{ comment.createdDate }}</p>
          <button @click="showPasswordInput(comment)">
            삭제
          </button>
          <button v-if="callDelete" @click="deleteComment(comment)">
            확인
          </button>
        </template>
        <template v-else>
          <p>삭제된 댓글입니다.</p>
        </template>
      </li>
    </ul>
    <button @click="handleLoadMore">더보기</button>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { deleteComment, requestAuthorization } from '@/api/boardDetail'

export default {
  data () {
    return {
      callDelete: false
    }
  },
  computed: {
    ...mapGetters(['getComments']),
    commentList () {
      return this.getComments
    }
  },
  methods: {
    async deleteComment (comment) {
      if (window.confirm('삭제하시겠습니까?')) {
        try {
          const token = await requestAuthorization(comment.id, comment.password)
          if (token) {
            await deleteComment(comment.id, token)
            comment.deleted = true
          }
        } catch (error) {
          console.error('댓글 삭제에 실패했습니다:', error)
        }
      }
    },
    showPasswordInput () {
      this.callDelete = !this.callDelete
    },
    confirmDeleteComment (comment) {
      if (window.confirm('삭제하시겠습니까?')) {
        this.$emit('deleteComment', comment) // 삭제 확인 시 이벤트를 상위 컴포넌트로 전달
      }
    },
    handleLoadMore () {
      // 추가 댓글 불러오는 로직 구현
    }
  }
}
</script>
