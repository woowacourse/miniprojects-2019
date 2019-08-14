const writeArticleButton = document.getElementById("write-article-button");

function writeArticle() {
    // todo : 로그인 확인 기능 추가
    location.href = "/articles/writing";
};

writeArticleButton.addEventListener("click", writeArticle);
