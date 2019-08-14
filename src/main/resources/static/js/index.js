const writeArticleButton = document.getElementById("write-article-button");
writeArticleButton.addEventListener("click", writeArticle());

const writeArticle = function() {
    // todo : 로그인 확인 기능 추가

    location.href = "/articles/writing";
};
