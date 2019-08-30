const getModalTemplate = (buttons, num) => {
    return `<div class="modal inactive modal-animation modal-num-${num}">
        <div class="modal-dialog-inner">
            <div class="modal-dialog-custom common-flex">
               ${buttons}
            </div>
        </div>
    </div>`
};

const getArticleTemplate = function (articleId, userId, userName, imageUrl, profileUrl, contents, comments, likeNum, liking, time) {
    return `<div class="article-card card widget-feed no-pdd mrg-btm-70" data-article-id = ${articleId} data-user-id = ${userId}>
                            <div class="feed-header padding-15">
                                <ul class="list-unstyled list-info">
                                    <li>
                                        <a href = "/$">
                                        <img class="thumb-img img-circle" src=${profileUrl}
                                             alt="">
                                             </a>
                                        <div class="info">
                                            <a href=""
                                               class="title no-pdd-vertical text-bold inline-block font-size-15">${userName}</a>
                                        </div>
                                    </li>
                                    <ul class="dropdown-menu">
                                        <li>
                                            <a class="pointer">
                                                <i class="ti-trash pdd-right-10 text-dark"></i>
                                                <span class="">게시글 삭제</span>
                                            </a>
                                        </li>
                                    </ul>
                                </ul>
                            </div>
                            <div class="feed-body no-pdd">
                              
                                <img class="article-image" src=${imageUrl} alt="">
                               
                            </div>
                            <ul class="feed-action pdd-horizon-15 pdd-top-5">
                                <li>
                                    <a class="like-btn" data-liking=${liking}>
                                        <i class="fa ${liking ? 'fa-heart' : 'fa-heart-o'} font-size-25"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="/articles/${articleId}">
                                        <i class="ti-comment font-size-22"></i>
                                    </a>
                                </li>
                                <li>
                                    <a class = "copy-btn">
                                        <i class="ti-export font-size-22"></i>
                                    </a>
                                </li>
                                <li class="float-right">
                                    <a href="" class="pd-right-0">
                                        <i class="fa fa-bookmark-o font-size-25"></i>
                                    </a>
                                </li>
                            </ul>
                            <div class="feedback-status-container pdd-horizon-15">
                                <p class="no-mrg pdd-left-5 d-inline-block">
                                    <span class="text-bold"><span class ="like-num">${likeNum}</span> Likes<span class="text-bold">
                                </p>
                            </div>
                            <div class="feed-footer">
                                <div class="comment">      
                                <ul class="list-unstyled list-info pdd-horizon-5">
                                        <li class="comment-item no-pdd">
                                            <div class = "comment-list">
                                                <div class="info pdd-left-15 pdd-vertical-5">
                                                   <a href=""
                                                   class="title no-pdd-vertical text-bold inline-block font-size-15">${userName}</a>
                                                   <span class="font-size-14">${contents}</span>
                                                </div>
                                                ${comments}
                                            </div>
                                           <time class="font-size-8 text-gray d-block">${time}</time>                           
                                     </li>
                                    </ul>
                                    <div class="add-comment relative">
                                        <textarea rows="1" class="comment-textarea form-control text-dark padding-15"
                                                  placeholder="댓글 달기..."></textarea>
                                        <div class="absolute top-5 right-0">
                                            <button class="comment-btn btn btn-default no-border text-gray">게시</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>`
};

const getCommentTemplate = (commentId, userName, contents) => {
    return `
    <div class="info pdd-left-15 pdd-vertical-5" data-comment-id = ${commentId} >
       <a href=""
       class="title no-pdd-vertical text-bold inline-block font-size-15">${userName}</a>
       <span class="font-size-14">${contents}</span>
    </div>
      `;
};

const getMypageArticleTemplate = function (articleId, imgUrl) {
    return `
    <div>
        <a href="/articles/${articleId}">
            <img src=${imgUrl}>
        </a>
    </div>`
};

const getAlertTemplate = (message) => {
    return `<div class = "alert-con">
        <div class = "alert-box">
        <p>${message}</p>
        </div>
        </div>`
};