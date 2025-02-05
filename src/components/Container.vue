<script setup>
import Post from './Post.vue';
import FilterBox from './FilterBox.vue';
import MyPage from './MyPage.vue';
import filterData from '@/data/Filterdata';
import { emitter } from '@/utils/eventBus';
import { ref, onMounted, onUnmounted } from 'vue';

defineProps(['posts', 'step', 'imageUrl']);
const emit = defineEmits(['PostText']); // emit 이벤트 사용할 때엔 명시적으로 밝혀주기

// 필터 업데이트 : mitt 활용하기
onMounted(() => {
  emitter.on('filter-selected', (selectedFilter) => {
    filter.value = selectedFilter;
  });
});

// 마운트는 직접 해제해주는 습관 들이기
onUnmounted(() => {
  emitter.off('filter-selected');
});

const filterList = ref(filterData);
const filter = ref('');

// 포스트 텍스트 emit 함수
const handleInput = (event) => {
  emit('PostText', event.target.value);
}
</script>

<template>
  <!-- 기본 포스트-->
  <div v-if="step==0">
    <Post v-for="(post, i) in posts" :key="'post-' + i" :posts="post"/>
    <!--  1) for 문법에서 key값 설정해주지 않으면 자동으로 index로 설정됨. 
          2) 컴포넌트 내부에서 직접 사용하지 않더라도 Vue가 내부적으로 처리하는 작업을 돕기 위해 바인딩을 하기도 함 ex) :key -->  
  </div>

  <!--필터 선택-->
  <div v-if="step==1">
    <div :class="`${filter} upload-image`" :style="{ backgroundImage:`url(${imageUrl})`}"></div>
    <!-- CSSgram 필터 클래스 사용. index.html에 추가해둠. -->
    <div class="filters">
      <FilterBox
        v-for="filterItem in filterList" 
        :key="filterItem" 
        :imageUrl="imageUrl" 
        :filter="filterItem" 
        :isSelected="filter === filterItem"  
      >
        {{ filterItem }}
      </FilterBox>
    </div>
  </div>

  <!--글 작성 페이지-->
  <div v-if="step==2">
    <div :class="`${filter} upload-image`" :style="{ backgroundImage:`url(${imageUrl})`}"></div>
    <div class="write">
      <textarea class="write-box" @input="handleInput" placeholder="write!"></textarea>
    </div>
  </div>

  <!--팔로우 페이지-->
  <div v-if="step==3">
    <MyPage></MyPage>
  </div>
</template>

<style>
.upload-image{
width: 100%;
height: 450px;
background: black;
background-size: contain;  /* 이미지의 전체를 보이게 합니다 */
background-position: center;  /* 이미지가 중앙에 오도록 설정 */
background-repeat: no-repeat; /* 이미지를 반복하지 않게 설정 */
}
.filters{
overflow-x:scroll;
white-space: nowrap;
}
.filters::-webkit-scrollbar {
height: 5px;
}
.filters::-webkit-scrollbar-track {
background: #f1f1f1; 
}
.filters::-webkit-scrollbar-thumb {
background: #888; 
border-radius: 5px;
}
.filters::-webkit-scrollbar-thumb:hover {
background: #555; 
}
.write{
margin-top: 10px;
display: flex;
justify-content: center;
}
.write-box {
background-color: black;
color: white;
border: none;
width: 90%;
height: 100px;
padding: 15px;
display: block;
outline: none;
}
</style>