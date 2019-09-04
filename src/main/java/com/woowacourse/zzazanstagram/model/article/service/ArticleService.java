package com.woowacourse.zzazanstagram.model.article.service;

import com.woowacourse.zzazanstagram.model.article.domain.Article;
import com.woowacourse.zzazanstagram.model.article.dto.ArticleMyPageResponse;
import com.woowacourse.zzazanstagram.model.article.dto.ArticleRequest;
import com.woowacourse.zzazanstagram.model.article.dto.ArticleResponse;
import com.woowacourse.zzazanstagram.model.article.exception.ArticleException;
import com.woowacourse.zzazanstagram.model.article.repository.ArticleRepository;
import com.woowacourse.zzazanstagram.model.follow.service.FollowService;
import com.woowacourse.zzazanstagram.model.hashtag.domain.ArticleHashtag;
import com.woowacourse.zzazanstagram.model.hashtag.service.ArticleHashtagService;
import com.woowacourse.zzazanstagram.model.member.domain.Member;
import com.woowacourse.zzazanstagram.model.member.dto.MemberMyPageResponse;
import com.woowacourse.zzazanstagram.model.member.dto.MemberResponse;
import com.woowacourse.zzazanstagram.model.member.service.MemberAssembler;
import com.woowacourse.zzazanstagram.model.member.service.MemberService;
import com.woowacourse.zzazanstagram.util.FileUploaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private static final Logger log = LoggerFactory.getLogger(ArticleService.class);
    private static final String TAG = "[ArticleService]";
    private static final int DEFAULT_PAGE_NUM = 0;

    private final ArticleRepository articleRepository;
    private final ArticleHashtagService articleHashtagService;
    private final MemberService memberService;
    private final FollowService followService;
    private final FileUploaderService fileUploaderService;
    private final String dirName;

    public ArticleService(ArticleRepository articleRepository, ArticleHashtagService articleHashtagService, MemberService memberService, FollowService followService, FileUploaderService fileUploaderService, @Value("${cloud.aws.s3.dirName.article}") String dirName) {
        this.articleRepository = articleRepository;
        this.articleHashtagService = articleHashtagService;
        this.memberService = memberService;
        this.followService = followService;
        this.fileUploaderService = fileUploaderService;
        this.dirName = dirName;
    }

    @Transactional
    public void save(ArticleRequest dto, String email) {
        Member author = memberService.findByEmail(email);
        MultipartFile file = dto.getFile();
        String imageUrl = fileUploaderService.uploadImage(file, dirName);
        Article article = ArticleAssembler.toEntity(dto, imageUrl, author);
        articleRepository.save(article);
        articleHashtagService.save(article);

        log.info("{} imageUrl : {}", TAG, imageUrl);
        log.info("{} create() >> {}", TAG, article);
    }

    public ArticleResponse findArticleResponseBy(Long articleId, String email) {
        Member loginMember = memberService.findByEmail(email);
        Article article = findById(articleId);

        return ArticleAssembler.toDto(article, loginMember);
    }

    public Article findById(Long articleId) {
        return articleRepository.findById(articleId).orElseThrow(() -> new ArticleException("해당 게시글을 찾을 수 없습니다."));
    }

    public List<ArticleResponse> fetchArticlePagesBy(Long lastArticleId, int size, Long loginMemberId) {
        Member loginMember = memberService.findById(loginMemberId);
        List<Member> followers = findFollowersWithLoggedInMember(loginMemberId, loginMember);
        Page<Article> articles = fetchPages(lastArticleId, size, followers);

        return ArticleAssembler.toDtos(articles.getContent(), loginMember);
    }

    private List<Member> findFollowersWithLoggedInMember(Long memberId, Member loginMember) {
        List<Long> followingsIds = followService.findFollowingsIds(memberId);
        List<Member> allMembers = memberService.findAllByIds(followingsIds);
        allMembers.add(loginMember);

        return allMembers;
    }

    private Page<Article> fetchPages(Long lastArticleId, int size, List<Member> followers) {
        PageRequest pageRequest = PageRequest.of(DEFAULT_PAGE_NUM, size);
        return articleRepository.findByIdLessThanAndAuthorInOrderByIdDesc(lastArticleId, followers, pageRequest);
    }

    public void deleteById(Long articleId, String email) {
        Article article = findById(articleId);
        Member member = memberService.findByEmail(email);
        article.checkAuthentication(member);
        articleRepository.delete(article);
    }

    public List<ArticleMyPageResponse> findArticleMyPageResponsesBy(Long lastArticleId, int size, String nickName) {
        MemberResponse memberResponse = memberService.findMemberResponseByNickName(nickName);
        PageRequest pageRequest = PageRequest.of(DEFAULT_PAGE_NUM, size);
        Page<Article> articles = articleRepository.findByIdLessThanAndAuthorIdEqualsOrderByIdDesc(lastArticleId
                , memberResponse.getId(), pageRequest);

        return articles.stream().map(ArticleAssembler::toMyPageDto).collect(Collectors.toList());
    }

    public List<ArticleResponse> findArticleResponsesBy(String keyword, Long memberId) {
        Member loginMember = memberService.findById(memberId);
        List<Article> articles = articleHashtagService.findAllByHashtag(keyword)
                .stream()
                .map(ArticleHashtag::getArticle)
                .collect(Collectors.toList());

        return Collections.unmodifiableList(ArticleAssembler.toDtos(articles, loginMember));
    }

    public MemberMyPageResponse findMemberMyPageResponseByNickName(String nickName) {
        Member member = memberService.findByNickName(nickName);
        Long id = member.getId();

        long articleNumber = countByAuthorId(id);
        long followerNumber = followService.countFollowers(id);
        long followeeNumber = followService.countFollowees(id);

        return MemberAssembler.toMyPageResponse(member, articleNumber, followerNumber, followeeNumber);
    }

    private long countByAuthorId(Long id) {
        return articleRepository.countArticleByAuthorId(id);
    }
}
