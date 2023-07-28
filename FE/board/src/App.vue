<template>
  <div id="app">
    <div class="header">
      <h1 class="board-title">NTS Board</h1>
      <CountBar :boardCount="boardCount" :commentCount="commentCount" />
    </div>
    <BoardList :boardList="boardList" />
    <SearchBar @search="onSearch" />
  </div>
</template>

<script>
import BoardList from './components/BoardList.vue'
import CountBar from './components/CountBar.vue'
import SearchBar from './components/SearchBar.vue'
import { mapState, mapActions } from 'vuex'

export default {
  components: {
    BoardList,
    CountBar,
    SearchBar
  },
  computed: {
    ...mapState(['boardList', 'boardCount', 'commentCount'])
  },
  methods: {
    ...mapActions(['fetchBoardList', 'fetchBoardAndCommentCount']),
    onSearch (searchKeyword) {
      this.$store.commit('SET_SEARCH_KEYWORD', searchKeyword)
      this.$store.dispatch('searchBoards')
    }
  },
  async created () {
    await this.fetchBoardList()
    await this.fetchBoardAndCommentCount()
  }
}
</script>
