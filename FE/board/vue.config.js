module.exports = {
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:7777', // 서버 주소 (포트 번호 제외)
        changeOrigin: true,
        pathRewrite: {
          '^/api': '/api' // 실제 요청 시 /api를 유지한 채로 요청을 보내도록 설정
        }
      }
    }
  }
}
