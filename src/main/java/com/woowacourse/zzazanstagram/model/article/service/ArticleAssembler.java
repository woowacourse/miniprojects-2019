package com.woowacourse.zzazanstagram.model.article.service;

import com.woowacourse.zzazanstagram.model.article.domain.Article;
import com.woowacourse.zzazanstagram.model.article.domain.vo.Contents;
import com.woowacourse.zzazanstagram.model.article.domain.vo.Image;
import com.woowacourse.zzazanstagram.model.article.dto.ArticleMyPageResponse;
import com.woowacourse.zzazanstagram.model.article.dto.ArticleRequest;
import com.woowacourse.zzazanstagram.model.article.dto.ArticleResponse;
import com.woowacourse.zzazanstagram.model.comment.domain.Comment;
import com.woowacourse.zzazanstagram.model.comment.dto.CommentResponse;
import com.woowacourse.zzazanstagram.model.comment.service.CommentAssembler;
import com.woowacourse.zzazanstagram.model.member.domain.Member;

import java.util.List;
import java.util.stream.Collectors;

public class ArticleAssembler {
    public static Article toEntity(ArticleRequest dto, String imageUrl, Member author) {
        Image image = Image.of(imageUrl);
        Contents contents = Contents.of(dto.getContents());

        return new Article(image, contents, author);
    }

    public static ArticleResponse toDto(Article article, Member loginMember) {
        List<Comment> comments = article.getComments();
        List<CommentResponse> commentResponses = comments.stream()
                .map(CommentAssembler::toDto)
                .collect(Collectors.toList());

        return ArticleResponse.ArticleResponseBuilder.anArticleResponse()
                .id(article.getId())
                .image(article.getImageValue())
                .contents(article.getContentsValue())
                .nickName(article.getAuthor().getNickNameValue())
                .profileImage(article.getAuthor().getProfileImageValue())
                .createdDate(article.getCreatedDate())
                .lastModifiedDate(article.getLastModifiedDate())
                .commentResponses(commentResponses)
                .ddabongCount(article.countClickedDdabong())
                .isDdabongClicked(article.isDdabongClicked(loginMember))
                .build();
    }

    public static List<ArticleResponse> toDtos(List<Article> articles, Member loginMember) {
        return articles.stream()
                .map(article -> ArticleAssembler.toDto(article, loginMember))
                .collect(Collectors.toList());
    }

    public static ArticleMyPageResponse toMyPageDto(Article article) {
        return new ArticleMyPageResponse(article.getId(), article.getImageValue(), article.countClickedDdabong(), article.getCommentsCount());
    }
}
