<template>
  <div id="app">
    <div class="header">
      <h1 class="board-title">NTS Board</h1>
      <CountBar />
    </div>
    <BoardList />
    <SearchBar @search="onSearch" />
  </div>
</template>

<script>
import BoardList from './components/BoardList.vue'
import CountBar from './components/CountBar.vue'
import SearchBar from './components/SearchBar.vue'
import { mapActions, mapGetters } from 'vuex'

export default {
  components: {
    BoardList,
    CountBar,
    SearchBar
  },
  computed: {
    ...mapGetters(['getBoardList']),
    boardList () {
      return this.getBoardList
    }
  },
  methods: {
    ...mapActions(['fetchBoardList', 'fetchBoardAndCommentCount']),
    async onSearch (searchKeyword) {
      this.$store.commit('SET_SEARCH_KEYWORD', searchKeyword)
      await this.$store.dispatch('searchBoards')
    }
  },
  async created () {
    await this.fetchBoardList()
    await this.fetchBoardAndCommentCount()
  }
}
</script>
