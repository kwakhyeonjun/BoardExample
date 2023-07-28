import axios from 'axios'

const API_BASE_URL = 'http://localhost:7777/api'

// 전체 게시물 가져오기
export async function fetchBoardList () {
  try {
    const response = await axios.get(`${API_BASE_URL}/`)
    return response.data
  } catch (error) {
    console.error('Error fetching boards:', error)
    return []
  }
}

// 게시물, 댓글 수 가져오기
export async function fetchBoardAndCommentCount () {
  try {
    const response = await axios.get(`${API_BASE_URL}/count`)
    console.log(response.data)
    return response.data
  } catch (error) {
    console.error('게시글과 댓글 개수를 불러오지 못했습니다:', error)
    return { totalBoards: 0, totalComments: 0 }
  }
}

// 게시물 검색 결과 가져오기
export async function searchBoards (searchOption, searchKeyword) {
  try {
    const response = await axios.get(`${API_BASE_URL}/search`, {
      params: {
        t: searchOption,
        q: searchKeyword
      }
    })
    console.log('게시물 검색 결과: ', response.data)
    return response.data
  } catch (error) {
    console.error('올바르지 않은 검색입니다.', error)
    return []
  }
}
