import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'

export const usePostStore = defineStore('post', () => {
    // defineStore : Pinia 스토어의 고유한 이름 지정
    const posts = ref([
        {
            id: 1,
            name: "Kim Hyun",
            userImage: "https://picsum.photos/100?random=3",
            postImage: "https://picsum.photos/600?random=3",
            likes: 36,
            date: "May 15",
            liked: false,
            content: "오늘 무엇을 했냐면요 아무것도 안했어요 ?",
            filter: "perpetua"
        },
        {
            id: 2,
            name: "John Doe",
            userImage: "https://picsum.photos/100?random=4",
            postImage: "https://picsum.photos/600?random=4",
            likes: 20,
            date: "Apr 20",
            liked: false,
            content: "흔한 자랑스타그램",
            filter: "clarendon"
        },
        {
            id: 3,
            name: "Minny",
            userImage: "https://picsum.photos/100?random=5",
            postImage: "https://picsum.photos/600?random=5",
            likes: 49,
            date: "Apr 4",
            liked: false,
            content: "우리집 개는 화장실 물도 내림",
            filter: "lofi"
        }
    ]);
    const updateLikes = (idx) => {
        if (posts.value[idx].liked) {
            posts.value[idx].likes++;
        } else {
            posts.value[idx].likes--;
        }
        posts.value[idx].liked = !posts.value[idx].liked;
    }

    const isLoading = ref(false)
    
    const loadMorePosts = () => {
        if (isLoading.value) return;
        isLoading.value = true;
        axios.get(`https://codingapple1.github.io/vue/more${posts.value.length-3}.json`)
        // 템플릿 리터럴(` 백틱) : 변수를 ${} 구문을 통해 문자열 안에 포함시키고 싶을 때에 사용. 일반 따옴표로 처리하면 x.
            .then(response => {
                posts.value.push(response.data);  // 새로운 데이터 배열을 기존 배열에 추가
            })
            .catch(error => {
                console.error("데이터를 불러오는 중 오류 발생:", error);
            })
            .finally(() => {
                isLoading.value = false;
            });
    }


    return {
        posts, updateLikes, isLoading, loadMorePosts
    }

})