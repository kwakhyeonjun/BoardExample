<template>
  <div class="like-container">
      <button @click="handleLike" :disabled="isLike">
      좋아요
    </button>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import { likeBoard } from '@/api/boardDetail'

export default {
  date () {
    return {
      boardId: ''
    }
  },
  computed: {
    ...mapGetters(['getIsLike']),
    isLike () {
      return this.getIsLike
    }
  },
  methods: {
    ...mapActions(['setIsLike', 'setLikeCount']),
    async handleLike () {
      try {
        await likeBoard(this.boardId)
        this.setLikeCount()
        this.setIsLike()
      } catch (error) {
        console.error('좋아요 처리에 실패했습니다:', error)
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

.like-container {
  display: flex;
  justify-content: center;
}

button {
  background-color: #03c75a;
  color: white;
  padding: 7px 12px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.2s;
}

button:hover {
  background-color: #048c40;
}

button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

</style>
