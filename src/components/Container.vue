<script setup>
import Post from './Post.vue';

defineProps(['posts']);
</script>

<template>
<div>
  <Post v-for="(post, i) in posts" :key="'post-' + i" :posts="post"/>
  <!--  1) for 문법에서 key값 설정해주지 않으면 자동으로 index로 설정됨. 
        2) 컴포넌트 내부에서 직접 사용하지 않더라도 Vue가 내부적으로 처리하는 작업을 돕기 위해 바인딩을 하기도 함 ex) :key -->  
</div>
</template>

<style></style>