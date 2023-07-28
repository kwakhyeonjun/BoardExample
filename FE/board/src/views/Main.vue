<template>
  <div id="main">
    <div class="header">
      <h1 class="board-title">NTS Board</h1>
      <CountBar />
    </div>
    <router-link to="/create">
      <button class="create-button">게시글 작성</button>
    </router-link>
    <BoardList />
    <SearchBar @search="onSearch" />
  </div>
</template>

<script>
import BoardList from '../components/BoardList.vue'
import CountBar from '../components/CountBar.vue'
import SearchBar from '../components/SearchBar.vue'
import { mapActions } from 'vuex'

export default {
  components: {
    BoardList,
    CountBar,
    SearchBar
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

<style>
.create-button {
  padding: 8px 16px;
  background-color: #007bff;
  color: #fff;
  border: none;
  cursor: pointer;
}
</style>
