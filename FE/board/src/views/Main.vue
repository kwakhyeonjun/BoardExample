<template>
  <div id="main">
    <div class="count-and-create">
      <CountBar />
      <router-link to="/create" class="create-link">
        <button class="create-button">게시글 작성</button>
      </router-link>
    </div>
    <div class="content">
      <div class="board-list">
        <h2>게시판 목록</h2>
        <BoardList />
      </div>
    </div>
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
body {
  font-family: Arial, sans-serif;
  margin: 0;
  padding: 0;
}

#main {
  background-color: #f0f0f0;
}

.count-and-create {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
}

.count-bar {
  display: flex;
  align-items: center;
}

.count-label {
  margin-right: 8px;
}

.create-button {
  background-color: #03c75a;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.2s;
}

.create-button:hover {
  background-color: #048c40;
}

.content {
  display: flex;
  justify-content: space-between;
  padding: 20px;
}

.board-list {
  flex: 1;
  margin-right: 20px;
}

.footer {
  background-color: #f0f0f0;
  padding: 10px 20px;
  text-align: center;
}
</style>
