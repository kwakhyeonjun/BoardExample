<template>
  <div class="comment-section">
    <h2>댓글</h2>
    <ul class="comment-list">
      <li v-for="comment in commentList" :key="comment.id">
        <template v-if="!comment.deleted">
          <div class="comment-content">
            <p>{{ comment.content }}</p>
            <p class="comment-info">
              <span class="writer">{{ comment.writer }}</span>
              <span class="created-date">{{ formatDate(comment.createdDate) }}</span>
            </p>
          </div>
          <input v-if="callDelete" v-model="comment.password" type="password" placeholder="비밀번호를 입력하세요" />
          <div class="comment-actions">
            <button @click="showPasswordInput(comment)">삭제</button>
            <button v-if="callDelete" @click="deleteComment(comment)">확인</button>
          </div>
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
    handleLoadMore () {
      // 추가 댓글 불러오는 로직 구현
    },
    formatDate (date) {
      const options = {
        year: 'numeric',
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
/* Comment Section Styling */
.comment-section {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.comment-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.comment-list li {
  margin-bottom: 20px;
}

.comment-content {
  font-size: 16px;
}

.comment-info {
  display: flex;
  justify-content: space-between;
  font-size: 14px;
  color: #888;
  margin-top: 10px;
}

.comment-info .writer {
  flex: 1;
  font-weight: bold;
  margin-right: 10px;
}

.comment-info .created-date {
  flex: 1;
  text-align: right;
}

.comment-actions {
  display: flex;
  justify-content: flex-end;
  margin-top: 10px;
}

.comment-actions button {
  background-color: #03c75a;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.2s;
  margin-left: 10px;
}

.comment-actions button:hover {
  background-color: #048c40;
}
</style>
