(async () => {
    let loggedInUser;
    let likedUsers;
    const request = new Request(`/api`);

    const like = new Like();

    const likeBtn = document.querySelector('.like-btn');
    const likeIcon = document.querySelector('#like-btn-icon');
    const likeNum = document.querySelector('.like-num');

    likeBtn.addEventListener('click', (event) => {
        const icon = likeIcon;

        if (icon.classList.contains("fa-heart-o")) {
            request.post(`/articles/${articleId}/likes`, null)
                .then(() => {
                    icon.classList.add("fa-heart");
                    icon.classList.remove("fa-heart-o");
                    likeNum.innerText = `${parseInt(likeNum.innerText) + 1}명이 좋아합니다.`;
                });
        } else {
            request.delete(`/articles/${articleId}/likes`)
                .then(() => {
                    icon.classList.add("fa-heart-o");
                    icon.classList.remove("fa-heart");
                    likeNum.innerText = `${parseInt(likeNum.innerText) - 1}명이 좋아합니다.`;
                });
        }
    });

// 로그인한 유저 정보 가져오기
    await request.get('/users/loggedin/', (status, data) => {
        loggedInUser = data;
        // 이 아티클을 좋아요하는 유저들 가져오기
    });

    await request.get(`/articles/${articleId}/likes`, (status, data) => {
        likedUsers = data;

        // 유저들 수가 곧 좋아요 수
        likeNum.innerText = `${likedUsers.length} 명이 좋아합니다.`;

        let liking;
        likedUsers.forEach((user) => {
            if (user.id === loggedInUser.id) {
                liking = true;
            }
        });

        if (liking) {
            likeIcon.classList.add('fa-heart');
        } else {
            likeIcon.classList.add('fa-heart-o');
        }
    });

    const copyBtn = document.querySelector('.copy-btn');
    copyBtn.addEventListener('click', (event) => {
        const temp = document.createElement("textarea");
        document.body.appendChild(temp);
        temp.value = `${window.location.host}/articles/${articleId}`;
        temp.select();
        document.execCommand('copy');
        document.body.removeChild(temp);
        new Alert("게시글 링크가 복사되었습니다!");
    });
})();