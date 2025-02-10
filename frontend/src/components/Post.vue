<script setup>
import { usePostStore } from '@/stores/usePostStore';

const postStore = usePostStore();
const { updateLikes } = postStore;

defineProps(['posts', 'index']);
</script>

<template>
<div class="post">
  <div class="post-header">
    <div class="profile" :style="{ backgroundImage: 'url(' + posts.userImage + ')' }"></div>
    <span class="profile-name">{{posts.name}}</span>
  </div>
  <div :class="`${posts.filter} post-body`" :style="{ backgroundImage: 'url(' + posts.postImage + ')' }"></div>
  <div class="post-content">
    <p @click="updateLikes(index)" class="likes">
      <i :class="posts.liked ? 'fas fa-heart' : 'far fa-heart'"></i>
      {{posts.likes}} Likes
    </p>
    <p><strong>{{posts.name}}</strong> {{posts.content}}</p>
    <p class="date">{{posts.date}}</p>
  </div>
</div> 
</template>

<style>
.post {
  width: 100%;
}
.profile {
  width: 30px;
  height: 30px;
  background-size: 100%;
  border-radius: 50%;
  float: left;
}
.profile-name {
  display: block;
  float: left;
  padding-left: 10px;
  padding-top: 7px;
  font-size: 14px;
}
.post-header {
  height: 30px;
  padding: 10px;
}
.post-body {
  height: 450px;
  background-position: center;
  background-size: cover;
}
.post-content {
  padding-left: 15px;
  padding-right: 15px;
  font-size: 14px;
}
.date {
  font-size: 11px;
  color: grey;
  margin-top: -8px;
} 
.likes {
  cursor: pointer;
}
.fas, .far {
  margin-right: 5px;
  color: red;
}
</style>