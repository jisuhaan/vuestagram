<script setup>
import { emitter } from '@/utils/eventBus';
import { ref } from 'vue';
const props = defineProps({
  imageUrl: String,
  filter: String,
  isSelected: Boolean
});

// 클릭 시 부모에게 필터 이름을 전달하는 함수
const selectFilter = (filter) => {
  emitter.emit('filter-selected', filter);
};
</script>

<template>
<div class="filter-item" :class="{ selected: isSelected }" @click="selectFilter(props.filter)" >
  <div class="filter-title">
    <!-- slot : 부모 컴포넌트에서 작성한 filter 콘텐츠를 동적으로 바인딩하기 위해 사용 -->
    <slot></slot> 
  </div>
  <div :class="`${props.filter} filter-image`" :style="{ backgroundImage:`url(${props.imageUrl})`}">
  </div>   
</div>
</template>

<style>
.filter-title{
  text-align: center;
  font-size: 12px;
  margin-top: 15px;
  margin-bottom: 5px;
}
.filter-item.selected {
  transform: scale(1.1);
  border: 2px solid #ff6600;
  box-shadow: 0 0 10px rgba(255, 102, 0, 0.5);
  background-color: rgba(255, 102, 0, 0.2);
}
.filter-item {
  width: 100px;
  height: auto;
  margin: 5px 5px 5px auto;
  display: inline-block;
  color : black;
  background-size: cover;
  background-position : center;
}
.filter-item:first-child{
  margin-left: 15px;
}
.filter-item:last-child{
  margin-right: 15px;
}
.filter-image{
  width: 100px;
  height: 100px;
  background-size: cover;
  background-position : center;
  display: inline-block;
}
</style>