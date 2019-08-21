package com.woowacourse.zzazanstagram.model.ddabong.service;

import com.woowacourse.zzazanstagram.model.article.domain.Article;
import com.woowacourse.zzazanstagram.model.article.service.ArticleService;
import com.woowacourse.zzazanstagram.model.ddabong.domain.Ddabong;
import com.woowacourse.zzazanstagram.model.ddabong.dto.DdabongResponse;
import com.woowacourse.zzazanstagram.model.ddabong.repository.DdabongRepository;
import com.woowacourse.zzazanstagram.model.member.domain.Member;
import com.woowacourse.zzazanstagram.model.member.service.MemberService;
import org.springframework.stereotype.Service;

@Service
public class DdabongService {
    private final DdabongRepository ddabongRepository;
    private final ArticleService articleService;
    private final MemberService memberService;

    public DdabongService(DdabongRepository ddabongRepository, ArticleService articleService, MemberService memberService) {
        this.ddabongRepository = ddabongRepository;
        this.articleService = articleService;
        this.memberService = memberService;
    }

    public DdabongResponse saveOrRemove(Long articleId, String memberEmail) {
        Article article = articleService.findArticleById(articleId);
        Member member = memberService.findByEmail(memberEmail);
        Ddabong ddabong = ddabongRepository.findByArticleAndMember(article, member);

        if (ddabong != null) {
            return delete(article, ddabong);
        }

        return save(article, new Ddabong(article, member));
    }

    private DdabongResponse delete(Article article, Ddabong ddabong) {
        article.deleteDdabong(ddabong);
        ddabongRepository.delete(ddabong);
        return DdabongAssembler.toDto(article.getDdabongCount(), true);
    }

    private DdabongResponse save(Article article, Ddabong ddabong) {
        ddabongRepository.save(ddabong);
        return DdabongAssembler.toDto(article.getDdabongCount(), false);
    }
}
