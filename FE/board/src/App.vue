<template>
  <div id="app">
    <div class="header">
      <h1 class="board-title">NTS Board</h1>
    </div>
    <BoardList :boardList="boardList" />
  </div>
</template>

<script>
import BoardList from './components/BoardList.vue'
import { fetchBoardList } from './api/api.js'

export default {
  components: {
    BoardList
  },
  data () {
    return {
      boardList: []
    }
  },
  async created () {
    this.boardList = await fetchBoardList()
  }
}
</script>
