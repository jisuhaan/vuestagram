<script setup>
import Container from './components/Container.vue';
import axios from '@/axios';
import { emitter } from '@/utils/eventBus';
import { usePostStore } from '@/stores/usePostStore';
import { ref, onMounted, onUnmounted } from 'vue';

const postListResponse = ref([]); // 포스트 리스트 저장할 배열
const limit = ref(3); // 한 번에 불러올 데이터 수
const isLoading = ref(false); // 로딩 상태

const postStore = usePostStore(); // 스토어 생성 함수 호출. *definStore로 만든 함수이기에 뒤에 () 붙이는 것
const { loadMorePosts, posts } = postStore; // 구조 분해 할당. 

const step = ref(0);
const imageUrl = ref('');
const filter = ref('');
const postText = ref(''); // 작성 게시글

// 에밋 처리
const handlePostText = (PostText) => {
  postText.value = PostText;
};

// 필터 업데이트 : mitt 활용하기
onMounted(() => {
  fetchPosts();  // 컴포넌트가 마운트될 때 데이터 불러오기
  emitter.on('filter-selected', (selectedFilter) => {
    filter.value = selectedFilter;
  });

  // 스크롤 이벤트 리스너 등록
  window.addEventListener('scroll', handleScroll);
});

onUnmounted(() => {
  emitter.off('filter-selected');
  window.removeEventListener('scroll', handleScroll); // 컴포넌트가 언마운트될 때 이벤트 리스너 제거
});


const fetchPosts = async (customLimit = limit.value) => {
  if (isLoading.value) return; // 이미 로딩 중이면 중복 호출 방지
  isLoading.value = true; // 로딩 상태 시작

  try {
    const offset = postListResponse.value.length;
    const response = await axios.get('/posts/more',{
      params: {
        offset: offset,
        limit: customLimit,
      }
    });
    console.log(response.data); // 응답 데이터 확인
    // 응답 받은 데이터를 배열에 추가
    if (response.data.body.length > 0) {
      postListResponse.value.push(...response.data.body);
    }
  } catch (error) {
    console.error('Error fetching posts:', error);
  } finally {
    isLoading.value = false;
  }
};

// 더보기 버튼 클릭 시
const loadMorePostsWithOne = () => {
  fetchPosts(1); // limit 값을 1로 설정
};

// 스크롤 이벤트 핸들러
const handleScroll = throttle(() => { // 쓰로틀링으로 성능 개선
  if (window.innerHeight + window.scrollY >= document.documentElement.scrollHeight - 5) {
    fetchPosts();
  }
}, 200);

// 메서드
// 사진 업로드
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

// 포스트 저장
const publish = () => {
  var newPost = {
    name: "David J",
    userImage: "https://picsum.photos/100?random=22",
    postImage: imageUrl.value,
    likes: 22,
    date: "May 15",
    liked: false,
    content: postText.value,
    filter: filter.value
  }
  postListResponse.unshift(newPost); 
  
  // postStore.posts는 ref가 아니라 Pinia의 state 변수이므로 .value 붙이면 x
  // defineStore 함수 내부에서는 ref를 자동으로 풀어서 반환해 주기 때문!
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
    <ul class="logo" :style="{ right: step === 3 ? '50px' : '0' }">
      <li v-if="step !== 3"><img src="./assets/logo.svg" class="logo" /></li>
      <li v-if="step === 3">followers</li>
    </ul>

    <!-- 상단 오른쪽 버튼 -->
    <ul class="header-button-right">
      <li v-if="step === 0" @click="step = 3;">follower</li>
      <li v-if="step === 1" @click="step++;">Next</li>
      <li v-if="step === 2" @click="publish">공유</li>
    </ul>
  </div>

  <Container :posts="postListResponse" :step="step" :imageUrl="imageUrl" @PostText="handlePostText" />

  <div class="flex-container" style="width:100%" v-if="step === 0">
      <button class="show-button" @click="loadMorePostsWithOne">더보기</button>
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