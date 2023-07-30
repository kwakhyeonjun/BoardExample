import axios from 'axios'
import { API_BASE_URL } from '../config'

// 게시글 상세 정보를 가져오는 API 호출 함수
export const fetchBoardDetail = async (boardId) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/board/${boardId}`)
    return response.data
  } catch (error) {
    console.error('게시글 상세 정보를 가져오는데 실패했습니다:', error)
    throw error
  }
}

// 게시글 좋아요 API 호출 함수
export const likeBoard = async (boardId) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/board/like/${boardId}`)
    return response.data
  } catch (error) {
    console.error('게시글 좋아요 요청에 실패했습니다:', error)
    throw error
  }
}

// 게시글 댓글 목록 가져오기 API 호출 함수
export const fetchComments = async (boardId, page) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/comment/${boardId}`)
    return response.data
  } catch (error) {
    console.error('댓글 목록을 가져오는데 실패했습니다:', error)
    throw error
  }
}

// 댓글 작성 API 호출 함수
export const createComment = async (commentData) => {
  try {
    const response = await axios.post(`${API_BASE_URL}/comment`, commentData)
    return response.data
  } catch (error) {
    console.error('댓글 작성에 실패했습니다:', error)
    throw error
  }
}

// 비밀번호를 전달하고 Authorization 헤더를 받는 함수
export const requestAuthorization = async (commentId, password) => {
  try {
    const response = await axios.post(`${API_BASE_URL}/password/comment/${commentId}`, { password })
    const token = response.data
    return token
  } catch (error) {
    console.error('비밀번호 확인에 실패했습니다:', error)
    throw error
  }
}

// Authorization을 요청 헤더에 넣고 삭제를 요청하는 함수
export const deleteComment = async (commentId, token) => {
  try {
    // const config = {
    //   headers: {
    //     Authorization: `Bearer ${token}`
    //   }
    // }
    await axios.delete(`${API_BASE_URL}/post/${commentId}`)
  } catch (error) {
    console.error('댓글 삭제에 실패했습니다:', error)
    throw error
  }
}
