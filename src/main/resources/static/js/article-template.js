const template = (function () {
    const article = `<div class="card widget-feed" data-object="article" data-article-id="{{id}}">
                    <div class="feed-header">
                        <ul class="list-unstyled list-info">
                            <li>
                                <img class="thumb-img img-circle" src="/images/default/eastjun_profile.jpg" alt="">
                                <div class="info">
                                    <a href="" class="title no-pdd-vertical text-semibold inline-block">{{authorName}}</a>
                                    <span class="sub-title">{{updatedTime}}</span>
                                    <a class="pointer absolute top-0 right-0" data-toggle="dropdown"
                                       aria-expanded="false">
                                        <span class="btn-icon text-dark">
                                            <i class="ti-more font-size-16"></i>
                                        </span>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li data-btn="update">
                                            <a class="pointer">
                                                <i class="ti-pencil pdd-right-10 text-dark"></i>
                                                <span id="article-update-{{id}}">게시글 수정</span>
                                            </a>
                                        </li>
                                        <li data-btn="delete">
                                            <a class="pointer">
                                                <i class="ti-trash pdd-right-10 text-dark"></i>
                                                <span id="article-delete-{{id}}">게시글 삭제</span>
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <div class="feed-body no-pdd">
                        <p>
                            <span data-object="article-contents">{{article-contents}}</span> <br>
                            <video data-object="article-video" src="{{article-videoUrl}}" width=320" height="240" controls></video>
                            <img data-object="article-image" src="{{article-imageUrl}}" height="100" width="100" alt="">
                        </p>
                    </div>
                    <ul class="feed-action pdd-btm-5 border bottom">
                        <li>
                            <i class="fa fa-thumbs-o-up text-info font-size-16 mrg-left-5"></i>
                            <span id="article-good-count-{{id}}" class="font-size-14 lh-2-1">{{numberOfGood}}</span>
                        </li>
                        <li class="float-right">
                            <span class="font-size-13">공유 78회</span>
                        </li>
                        <li class="float-right mrg-right-15">
                            <span class="font-size-13">댓글 2개</span>
                        </li>
                    </ul>
                    <ul class="feed-action border bottom d-flex">
                        <li class="text-center flex-grow-1" data-btn="article-reaction-good-btn">
                            <button class="btn btn-default no-border pdd-vertical-0 no-mrg width-100">
                                <i id="article-good-btn-icon-{{id}}" class="fa fa-thumbs-o-up font-size-16"></i>
                                <span class="font-size-13">좋아요</span>
                            </button>
                        </li>
                        <li class="text-center flex-grow-1" data-list="comment-list">
                            <button class="btn btn-default no-border pdd-vertical-0 no-mrg width-100">
                                <i class="fa fa-comment-o font-size-16"></i>
                                <span class="font-size-13">댓글</span>
                            </button>
                        </li>
                        <li class="text-center flex-grow-1">
                            <button class="btn btn-default no-border pdd-vertical-0 no-mrg width-100">
                                <i class="fa fa-share font-size-16"></i>
                                <span class="font-size-13">공유하기</span>
                            </button>
                        </li>
                    </ul>
                    <div class="feed-footer">
                        <div class="comment">
                            <!--댓글 추가 공간-->
                            <ul id="comment-list-{{id}}" class="list-unstyled list-info"></ul>
                            <!-- 댓글 입력 공간-->
                            <div class="add-comment">
                                <textarea id="comment-value-{{id}}" rows="1" class="form-control comment-save" placeholder="댓글을 입력하세요."></textarea>
                            </div>
                        </div>
                    </div>
                </div>`;

    const comment = `<li class="comment-item" data-object="comment" data-comment-id="{{id}}">
                        <img class="thumb-img img-circle" src="/images/default/eastjun_profile.jpg" alt="프로필이미지">
                        <div class="info">
                            <div class="bg-lightgray border-radius-18 padding-10 max-width-100">
                                <a href="" class="title text-bold inline-block text-link-color">{{user-name}}</a>
                                <span id="comment-contents-{{id}}"data-object="comment-contents">{{comment-contents}}</span>
                            </div>
                            <a class="pointer absolute top-0 right-0" data-toggle="dropdown" aria-expanded="false">
                                <span class="btn-icon text-dark">
                                    <i class="ti-more font-size-16"></i>
                                </span>
                            </a>
                            <ul class="dropdown-menu">
                                <li data-btn="comment-update">
                                    <a class="pointer">
                                        <i class="ti-pencil pdd-right-10 text-dark"></i>
                                        <span id="comment-update-{{id}}" data-toggle="modal" data-target="#default-modal">댓글 수정</span>
                                    </a>
                                </li>
                                <li data-btn="comment-delete">
                                    <a class="pointer">
                                        <i class="ti-trash pdd-right-10 text-dark"></i>
                                        <span id="comment-delete-{{id}}">댓글 삭제</span>
                                    </a>
                                </li>
                            </ul>
                            <div class="font-size-12 pdd-left-10 pdd-top-5">
                                <span class="pointer text-link-color" data-btn="comment-reaction-good-btn">좋아요</span>
                                <span>·</span>
                                <span class="pointer text-link-color" data-comment-list="comment-subList">답글 달기</span>
                                <span>·</span>
                                <span class="pointer">{{updatedTime}}</span>
                                <i id="comment-good-btn-icon-{{id}}" class="fa fa-thumbs-o-up text-info font-size-16"></i>
                                <span id="comment-good-count-{{id}}" class="font-size-14 lh-2-1">{{numberOfGood}}</span>
                            </div>
                            <div class="comment">
                                <!--여기에 입력공간 추가-->
                                <div class="sub-comment-input-area"></div>
                                <!--서브 댓글 추가 공간-->
                                <ul id="comment-sublist-{{id}}" class="list-unstyled list-info"></ul>
                            </div>
                        </div>
                    </li>`;

    const subComment = `<li class="comment-item" data-object="comment" data-comment-id="{{id}}">
                            <img class="thumb-img img-circle" src="/images/default/eastjun_profile.jpg" alt="프로필이미지">
                            <div class="info">
                                <div class="bg-lightgray border-radius-18 padding-10 max-width-100">
                                    <a href="" class="title text-bold inline-block text-link-color">{{user-name}}</a>
                                    <span id="comment-contents-{{id}}"data-object="comment-contents">{{comment-contents}}</span>
                                </div>
                                <a class="pointer absolute top-0 right-0" data-toggle="dropdown" aria-expanded="false">
                                    <span class="btn-icon text-dark">
                                        <i class="ti-more font-size-16"></i>
                                    </span>
                                </a>
                                <ul class="dropdown-menu">
                                    <li data-btn="comment-update">
                                        <a class="pointer">
                                            <i class="ti-pencil pdd-right-10 text-dark"></i>
                                            <span id="comment-update-{{id}}" data-toggle="modal" data-target="#default-modal">댓글 수정</span>
                                        </a>
                                    </li>
                                    <li data-btn="comment-delete">
                                        <a class="pointer">
                                            <i class="ti-trash pdd-right-10 text-dark"></i>
                                            <span id="comment-delete-{{id}}">댓글 삭제</span>
                                        </a>
                                    </li>
                                </ul>
                                <div class="font-size-12 pdd-left-10 pdd-top-5">
                                    <span class="pointer text-link-color">좋아요</span>
                                    <span>·</span>
                                    <span class="pointer text-link-color">답글 달기</span>
                                    <span>·</span>
                                    <span class="pointer">{{updatedTime}}</span>
                                </div>
                            </div>
                        </li>`;

    const commentArea = `<div class="add-comment">
                            <textarea id="parent-comment-value-{{id}}" data-parents-comment-id="{{id}}" rows="1" class="form-control comment-save" placeholder="댓글을 입력하세요."></textarea>
                          </div>`;

    return {
        article: article,
        comment: comment,
        subComment: subComment,
        commentArea: commentArea,
    };
})();