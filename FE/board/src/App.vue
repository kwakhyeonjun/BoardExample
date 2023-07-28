<template>
  <div id="app">
    <div class="header">
      <h1 class="board-title">NTS Board</h1>
      <CountBar :boardCount="boardCount" :commentCount="commentCount" />
    </div>
    <BoardList :boardList="boardList" />
  </div>
</template>

<script>
import BoardList from './components/BoardList.vue'
import CountBar from './components/CountBar.vue'
import { mapState, mapActions } from 'vuex'

export default {
  components: {
    BoardList,
    CountBar
  },
  computed: {
    ...mapState(['boardList', 'boardCount', 'commentCount'])
  },
  methods: {
    ...mapActions(['fetchBoardList', 'fetchBoardAndCommentCount'])
  },
  async created () {
    await this.fetchBoardList()
    await this.fetchBoardAndCommentCount()
  }
}
</script>
