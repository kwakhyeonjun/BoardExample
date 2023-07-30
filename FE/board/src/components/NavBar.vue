<template>
  <div>
    <div class="header">
      <router-link to="/" class="logo-link">
        <h1 class="logo-text">NTS Board</h1>
      </router-link>
      <CountBar />
    </div>
  </div>
</template>

<style>
.header {
  background-color: white;
  color: #03c75a;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 20px;
}

.logo-link {
  text-decoration: none;
  color: #03c75a;
  font-size: 24px;
}

.logo-text {
  margin: 0;
}
</style>
