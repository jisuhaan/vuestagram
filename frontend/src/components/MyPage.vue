<script setup>
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';

const followers = ref([]);  // 화면에 보여지는 팔로워 리스트
const followersOrigin = ref([]);  // 원본 데이터 저장 보관. 혹시 원본 데이터가 수정될 것을 염려하여 필요. ex) 팔로워 삭제 요청 실패 시 목록 원복 필요.
const searchInput = ref(""); // 서치창 입력값 : v-model 연결

onMounted(() => {
  axios.get('/follower.json').then((response)=>{
    followers.value = response.data; // axios의 get() 메서드는 HTTP 요청을 보내고 응답을 받아올 때 response 객체로 반환한다. 그 안에서 필요한 data를 읽어오는 것.
    followersOrigin.value = [...followers.value]; // 참조가 아니라 새로운 배열을 갖도록 해야 한다. 그래야 followers가 변경되도 followersOrigin은 기존값 그대로 갖고 있을 수 있음.
    // 기본적으로 followers.value는 배열. 배열도 객체처럼 참조로 다뤄지기 때문! (객체 복사 주의!!)
  })
})

const filteredFollowers = computed(() => {
  return searchInput.value ? 
    followers.value.filter((follower) => follower.name.includes(searchInput.value)) : 
    followers.value;
});

const removeFollower = (followerId) => {
  try {
    followers.value = followers.value.filter(f => f.id !== followerId); // 화면에서 삭제
    followersOrigin.value = [...followers.value]; // 원본도 업데이트
  } catch (error) {
    alert("삭제 실패! 다시 시도해주세요.");
    followers.value = [...followersOrigin.value]; // 실패 시 원래대로 복구
  }
};
</script>

<template>
  <div class="followers">
    <div class="followers__search-container">
      <input class="followers__search-box" placeholder="🔍 검색" v-model="searchInput" />
    </div>
    <div class="followers__list">
      <div class="followers__item" v-for="follower in filteredFollowers" :key="follower.id">
        <div class="followers__item-profile" :style="{ backgroundImage: `url(${follower.image})` }"></div>
        <span class="followers__item-name">{{ follower.name }}</span>
        <button class="followers__remove-button" @click="removeFollower(follower.id)">
          <i class="fas fa-times"></i>
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.followers {
  padding: 10px;
}

.followers__search-container {
  display: flex;
  justify-content: center;
  margin-bottom: 15px;
}

.followers__search-box {
  width: 90%;
  height: 40px;
  background-color: #f1f1f1;
  border: 1px solid #ccc;
  border-radius: 8px;
  padding-left: 10px;
  font-size: 16px;
  box-sizing: border-box;
}

.followers__list {
  margin-top: 40px;
}

.followers__item {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  position: relative;
  padding-left: 10px;
  padding-right: 10px;
}

.followers__item-profile {
  width: 45px;
  height: 45px;
  background-size: cover;
  background-position: center;
  border-radius: 50%;
  margin-right: 12px;
}

.followers__item-name {
  font-size: 16px;
  color: #333;
  flex-grow: 1;
}

.followers__remove-button {
  background: transparent;
  border: none;
  font-size: 18px;
  color: #f00;
  cursor: pointer;
  transition: transform 0.3s ease, color 0.2s ease;
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
}

.followers__remove-button:hover {
  transform: scale(1.2) translateY(-50%);
  color: #d80000;
}

.followers__remove-button:focus {
  outline: none;
}
</style>