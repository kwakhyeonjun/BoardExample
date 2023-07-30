<template>
  <div class="create-board">
    <h2>게시글 작성</h2>
    <form @submit.prevent="submitBoard">
      <div class="form-field">
        <label for="title">제목:</label>
        <input v-model="title" type="text" id="title" required />
      </div>
      <div class="form-field">
        <label for="writer">작성자:</label>
        <input v-model="writer" type="text" id="writer" required />
      </div>
      <div class="form-field">
        <label for="password">비밀번호</label>
        <input v-model="password" type="password" id="password" name="password" required />
      </div>
      <div class="form-field">
        <label for="content">내용:</label>
        <textarea v-model="content" id="content" required></textarea>
      </div>
      <div class="form-field">
        <label for="hashtagList">해시태그 (최대 5개):</label>
        <input
          v-model="tagInput"
          type="text"
          id="hashtagList"
          @keydown.enter.prevent
          @keydown.enter="addHashtag"
        />
        <button type="button" @click="addHashtag">추가</button>
      </div>
      <ul>
        <li v-for="tag in hashtagList" :key="tag">{{ tag }}</li> <!-- 해시태그 리스트 -->
      </ul>
      <div v-if="hashtagList.length > 5" class="error">해시태그는 5개까지 등록할 수 있습니다.</div>
      <div class="form-field">
        <button type="submit">작성 완료</button>
      </div>
    </form>
  </div>
</template>

<script>
import { createBoard } from '@/api/createBoard.js'

export default {
  data () {
    return {
      title: '',
      writer: '',
      content: '',
      password: '',
      hashtagList: [],
      tagInput: ''
    }
  },
  methods: {
    addHashtag () {
      if (!this.tagInput) return

      // 최대 5개까지만 추가
      if (this.hashtagList.length >= 5) {
        alert('해시태그는 5개까지 등록할 수 있습니다.')
        return
      }

      // 입력한 해시태그를 리스트에 추가
      this.hashtagList.push(this.tagInput)
      // 입력 폼 초기화
      this.tagInput = ''
    },
    async submitBoard () {
      if (this.hashtagList.length > 5) {
        return
      }

      // 게시글 등록 로직 수행
      const newBoard = {
        title: this.title,
        writer: this.writer,
        content: this.content,
        password: this.password,
        hashtagList: this.hashtagList.filter((tag) => tag.trim() !== '')
      }

      try {
        const response = await createBoard(newBoard)
        console.log('게시글 작성 완료!', response)
        this.$router.push(`/board/${response.id}`)
      } catch (error) {
        console.error('게시글 작성 실패:', error)
      }
    }
  }
}
</script>

<style>
body {
  margin: 0;
  font-family: Arial, sans-serif;
}

.create-board {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.create-board h2 {
  font-size: 24px;
  margin-bottom: 20px;
}

.form-field {
  margin-bottom: 20px;
}

.form-field label {
  display: block;
  font-weight: bold;
}

.form-field input,
.form-field textarea {
  width: 100%;
  padding: 5px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.form-field textarea {
  height: 150px;
}

ul {
  list-style: none;
  margin: 0;
  padding: 0;
}

ul li {
  margin-bottom: 5px;
}

.error {
  color: red;
  font-size: 14px;
  margin-top: 5px;
}

.form-field button {
  background-color: #03c75a;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.2s;
}

.form-field button:hover {
  background-color: #048c40;
}
</style>
