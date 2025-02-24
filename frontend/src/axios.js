import axios from 'axios';

const instance = axios.create({
  baseURL: 'http://localhost:8082/api/',  // Spring Boot 서버 URL
  timeout: 10000,  // 요청 타임아웃 시간 설정
  headers: {
    'Content-Type': 'application/json',
  },
});

export default instance;