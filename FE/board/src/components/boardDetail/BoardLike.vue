<template>
  <div>
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
