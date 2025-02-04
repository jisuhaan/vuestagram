<script setup>
import Container from './components/Container.vue';
import Postdata from './data/Postdata.js'
import axios from 'axios'
import { ref } from 'vue';

const buttonCount = ref(0);
const step = ref(0);
const imageUrl = ref('');
const posts = ref(Postdata); // Postdata에 새로운 데이터 추가됐을 때, 자동으로 템플릿에 반영되도록 ref로 선언.
const filter = ref('');
const postText = ref('');

// 에밋 처리
const handlePostText = (PostText) => {
  postText.value = PostText.text;
  filter.value = PostText.filter;
};

// 메서드
// 1) 포스트 더보기 버튼
const showMore = () => {
  axios.get(`https://codingapple1.github.io/vue/more${buttonCount.value}.json`)
  // 템플릿 리터럴(` 백틱) : 변수를 ${} 구문을 통해 문자열 안에 포함시키고 싶을 때에 사용. 일반 따옴표로 처리하면 x.
    .then(response => {
      buttonCount.value++;
      posts.value.push(response.data);
    })
    .catch(error => {
      console.error("데이터를 불러오는 중 오류 발생:", error);
    });
};
// 2) 사진 업로드
const upload = (e) => {
  let file = e.target.files[0];
  // e.target.files[0]은 Blob 객체. 
  // *Blob은 미디어 파일이나, 파일로서 저장될 수 있는 데이터를 표현할 때 사용. 사용자가 선택한 파일은 이미 Blob 객체 형태로 존재

  // 파일 확장자 확인 (이미지 파일만 허용)
  const allowedExtensions = ['.jpg', '.jpeg', '.png', '.gif'];
  const fileExtension = file.name.split('.').pop().toLowerCase(); // 파일 확장자 추출
  if (allowedExtensions.includes(`.${fileExtension}`)){
    imageUrl.value = URL.createObjectURL(file); 
    step.value++;
  } else {
    alert("이미지 파일만 업로드 가능합니다."); // 이미지가 아닌 경우 경고
  }
};

// 3) 포스트 저장
const publish = () => {
  var post = {
    name: "David J",
    userImage: "https://picsum.photos/100?random=22",
    postImage: imageUrl.value,
    likes: 22,
    date: "May 15",
    liked: false,
    content: postText.value,
    filter: filter.value
  }
  posts.value.unshift(post); 
  // unshift: 배열 맨 위에 요소 추가
  step.value = 0;
};
</script>

<template>
  <div class="header">
    <!-- 상단 왼쪽 버튼 -->
    <ul class="header-button-left">
      <li v-if="step === 0">Vuestagram</li>
      <li v-if="step !== 0 && step !== 3" @click="step = 0">x</li>
      <li v-if="step === 3" @click="step = 0">&lt;</li>
    </ul>

    <!-- 상단 가운데 텍스트 -->
    <ul class="logo">
      <li v-if="step !== 3"><img src="./assets/logo.svg" class="logo" /></li>
      <li v-if="step === 3">followers</li>
    </ul>

    <!-- 상단 오른쪽 버튼 -->
    <ul class="header-button-right">
      <li v-if="step === 0" @click="step = 3;">follower</li>
      <li v-if="step === 1" @click="step++;">Next</li>
      <li v-if="step === 2" @click="publish" @PostText="publish">공유</li>
    </ul>
  </div>

  <Container :posts="posts" :step="step" :imageUrl="imageUrl" @PostText="handlePostText" />

  <div class="flex-container" style="width:100%" v-if="step === 0">
      <button class="show-button" @click="showMore">더보기</button>
  </div>

  <div class="flex-container" v-if="step==0">
    <div class="footer">
      <ul class="footer-button-plus">
        <input @change="upload" type="file" id="file" class="inputfile" /> <!-- id="file"은 <label>과 연결되기 위해 사용 -->
        <label for="file" class="input-plus">+</label> <!-- input창은 가리고, 라벨만 보여준다. -->
      </ul>
    </div>
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
.flex-container{
  display: flex;
  justify-content: center;
}
</style>