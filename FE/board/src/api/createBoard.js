import axios from 'axios'

// 게시물 생성 API 호출 함수
export const createBoard = async (newBoard) => {
  try {
    const response = await axios.post('http://localhost:7777/api/board', newBoard)
    return response.data
  } catch (error) {
    console.error('게시글 작성 실패:', error)
    throw error
  }
}
