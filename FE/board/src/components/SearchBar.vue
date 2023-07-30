<template>
  <div class="search-bar">
    <select v-model="selectedOption" class="search-option">
      <option value="TITLE">제목</option>
      <option value="CONTENT">내용</option>
      <option value="WRITER">작성자</option>
      <option value="HASHTAG">해시태그</option>
    </select>
    <input
      v-model="searchKeyword"
      type="text"
      class="search-input"
      placeholder="검색어를 입력하세요..."
    />
    <button @click="search">검색</button>
    <div v-if='searchResult.length === 0 && isSearched' class='no-results'>
      검색 결과가 없습니다.
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      searchKeyword: '',
      selectedOption: 'TITLE', // 기본값으로 "제목" 선택
      isSearched: false
    }
  },
  computed: {
    searchResult () {
      return this.$store.state.searchStore.searchResult
    }
  },
  methods: {
    search () {
      if (this.searchKeyword.trim() === '') {
        return
      }
      this.$store.commit('SET_SEARCH_OPTION', this.selectedOption)
      this.$store.commit('SET_SEARCH_KEYWORD', this.searchKeyword)
      this.$store.dispatch('searchBoards')
    }
  }
}
</script>

<style>
.search-bar {
  display: flex;
  justify-content: center;
  align-items: center;
}

.search-option,
.search-input,
button {
  margin-right: 10px;
}
</style>
