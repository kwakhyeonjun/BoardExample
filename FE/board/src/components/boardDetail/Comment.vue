<template>
  <div>
    <CreateComment />
    <CommentList />
  </div>
</template>

<script>
import CreateComment from './CreateComment'
import CommentList from './CommentList'

export default {
  components: {
    CreateComment,
    CommentList
  }
}
</script>
