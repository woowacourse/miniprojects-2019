
const getArticleTemplate =  function(userName,imageUrl,profileUrl,contents) {
    `
                        <div class="article-card card widget-feed no-pdd mrg-btm-70">
                            <div class="feed-header padding-15">
                                <ul class="list-unstyled list-info">
                                    <li>
                                        <img class="thumb-img img-circle" src=${imageUrl}
                                             alt="">
                                        <div class="info">
                                            <a href=""
                                               class="title no-pdd-vertical text-bold inline-block font-size-15">${userName}</a>
                                        </div>
                                    </li>
                                    <a class="pointer absolute top-10 right-0" data-toggle="dropdown"
                                       aria-expanded="false">
                                        <span class="btn-icon text-dark">
                                            <i class="ti-more font-size-16"></i>
                                        </span>
                                    </a>
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
                                <img class="img-fluid" src="/images/default/sample_img_02.jpg" alt="">
                            </div>
                            <ul class="feed-action pdd-horizon-15 pdd-top-5">
                                <li>
                                    <a href="">
                                        <i class="fa fa-heart-o font-size-25"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="">
                                        <i class="ti-comment font-size-22"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="">
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
                                <img class="mini-profile-img" src=${profileUrl}>
                                <p class="no-mrg pdd-left-5 d-inline-block">
                                    <span class="text-bold">11 Likes<span class="text-bold">
                                </p>
                            </div>
                            <div class="feed-footer">
                                <div class="comment">
                                    <ul class="list-unstyled list-info pdd-horizon-5">
                                        <li class="comment-item no-pdd">
                                            <div class="info pdd-left-15 pdd-vertical-5">
                                                <a href=""
                                                   class="title no-pdd-vertical text-bold inline-block font-size-15">${userName}</a>
                                                <span class="font-size-14">${contents}</span>
                                                <time class="font-size-8 text-gray d-block">12시간 전</time>
                                            </div>
                                        </li>
                                    </ul>
                                    <div class="add-comment relative">
                                        <textarea rows="1" class="form-control text-dark padding-15"
                                                  placeholder="댓글 달기..."></textarea>
                                        <div class="absolute top-5 right-0">
                                            <button class="btn btn-default no-border text-gray">게시</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
`

}
const Index = (function () {
    const IndexController = function () {
        const indexService = new IndexService();
        const loadInit = function () {

        }



        const init = function () {
            loadInit()

        }

        return {
            init: init
        }
    }


    const IndexService = function () {
        const indexRequest = new Request("/api/main");
        const getPageData = (pageNum)=>{
            indexRequest.get('?page='+pageNum+"&size=10")
        }
        return {

        }
    }

    const init = () => {
        const indexController = new IndexController();
        indexController.init();
    }

    return {
        init: init
    }

}());

Index.init();