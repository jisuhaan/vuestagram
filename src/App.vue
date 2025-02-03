<script setup>
import Container from './components/Container.vue';
import Postdata from './data/Postdata.js'
import axios from 'axios'
import { ref } from 'vue';

// 게시물 데이터
const posts = ref(Postdata);

// 메서드
const showMore = () => {
  axios.get('https://codingapple1.github.io/vue/more0.json')
    .then(response => {
      posts.value.push(response.data);
    })
    .catch(error => {
      console.error("데이터를 불러오는 중 오류 발생:", error);
    });
};
</script>

<template>
  <div class="header">
    <ul class="header-button-left">
      <li>Cancel</li>
    </ul>
    <ul class="header-button-right">
      <li>Next</li>
    </ul>
    <img src="./assets/logo.svg" class="logo" />
  </div>

  <Container :posts="posts" />
  <button class="show-button" @click="showMore">
    더보기
  </button>

  <div class="footer">
    <ul class="footer-button-plus">
      <input type="file" id="file" class="inputfile" />
      <label for="file" class="input-plus">+</label>
    </ul>
  </div>
</template>

<style>
.show-button{
  border: 1px solid #191919;
  border-radius: 30px;
  background-color: #191919;
  padding: 2px 7px;
  color: white;
  transition: background-color 0.3s, color 0.3s; /* 부드러운 전환 효과 */
}
.show-button:hover {
  background-color: white;
  color: #191919;
  border: 1px solid #191919;
}
</style>