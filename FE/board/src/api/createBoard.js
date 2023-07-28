import axios from 'axios'
import { API_BASE_URL } from '../config'

// 게시물 생성 API 호출 함수
export const createBoard = async (newBoard) => {
  try {
    const response = await axios.post(`${API_BASE_URL}/board`, newBoard)
    return response.data
  } catch (error) {
    console.error('게시글 작성 실패:', error)
    throw error
  }
}
