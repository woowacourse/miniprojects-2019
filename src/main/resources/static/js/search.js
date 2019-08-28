// 서치 리스트 엘리먼트 찾기
let searchList = document.querySelector('.search-result-con');
let searchForm = document.querySelector('.search-form');
// 값이 변할 때마다 수행할 이벤트 등록
searchForm.addEventListener('input', (event) => {
    // 서치 리스트에 현재 들어있는 값 가져오기
    let query = event.target.value;
    if(query.length===0){
        return searchList.innerText=""
    }
    // ajax 호출

    const searchRequest = new Request("/api/search");
    searchRequest.get(`/${query}`,(status,data)=>{
        console.log(data);
        searchList.innerText="";
        // 쿼리 결과로 온 유저와 태그 가져오기
        const users = data.userInfoDtos;
        const tags = data.hashTags;

        // 가져온 값을 서치 리스트에 추가
        for (user of users) {
            searchList.insertAdjacentHTML('beforeend', `<a href="/${user.userContentsDto.userName}"><p>${user.userContentsDto.userName}</p></a>`);
        }

        for (tag of tags) {
            searchList.insertAdjacentHTML('beforeend', `<a href="/tags/${tag.name.slice(1,tag.name.length)}"><p>${tag.name}</p></a>`);
        }
    })
});

