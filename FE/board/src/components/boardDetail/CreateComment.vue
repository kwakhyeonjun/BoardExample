<template>
  <div class="comment-form">
    <form @submit.prevent="handleSubmit">
      <div class="left-column">
        <div class="form-field">
          <label for="writer">작성자</label>
          <input v-model="writer" type="text" id="writer" required />
        </div>
        <div class="form-field">
          <label for="password">비밀번호</label>
          <input v-model="password" type="password" id="password" required />
        </div>
      </div>
      <div class="right-column">
        <div class="form-field">
          <label for="content">댓글 내용</label>
          <textarea v-model="commentContent" id="content" placeholder="댓글을 입력하세요" required></textarea>
        </div>
        <div class="submit-button">
          <button type="submit">작성</button>
        </div>
      </div>
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
        this.writer = ''
        this.password = ''
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

<style>
body {
  margin: 0;
  font-family: Arial, sans-serif;
}

.left-column {
  flex: 1;
  padding-right: 20px;
}

.left-column .form-field {
  margin-bottom: 10px;
}

.left-column .form-field label {
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
}

.left-column .form-field input,
.left-column .form-field textarea {
  width: 100%;
  padding: 5px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.right-column {
  flex: 3;
}

.right-column .form-field {
  margin-bottom: 10px;
}

.right-column .form-field label {
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
}

.right-column .form-field textarea {
  width: 100%;
  height: 100px;
  padding: 5px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.submit-button {
  flex: 1;
  display: flex;
  justify-content: flex-end;
  align-items: flex-end;
  margin-top: 10px;
}

button {
  background-color: #03c75a;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.2s;
}

button:hover {
  background-color: #048c40;
}
</style>
