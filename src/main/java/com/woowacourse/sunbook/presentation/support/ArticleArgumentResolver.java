package com.woowacourse.sunbook.presentation.support;

import com.amazonaws.util.IOUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.sunbook.application.dto.article.ArticleRequestDto;
import com.woowacourse.sunbook.domain.Content;
import com.woowacourse.sunbook.domain.article.ArticleFeature;
import com.woowacourse.sunbook.domain.article.OpenRange;
import com.woowacourse.sunbook.domain.fileurl.FileUrl;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ArticleArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String JSONBODYATTRIBUTE = "JSON_REQUEST_BODY";
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return ArticleRequestDto.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
            throws Exception {
        String jsonBody = getRequestBody(webRequest);
        JsonNode jsonNode = objectMapper.readTree(jsonBody);
        Content contents = new Content(jsonNode.path("contents").asText());
        FileUrl imageUrl = new FileUrl(jsonNode.path("imageUrl").asText());
        FileUrl videoUrl = new FileUrl(jsonNode.path("videoUrl").asText());
        ArticleFeature articleFeature = new ArticleFeature(contents, imageUrl, videoUrl);

        return new ArticleRequestDto(articleFeature,
                OpenRange.of(Integer.parseInt(jsonNode.path("openRange").asText())));
    }

    private String getRequestBody(NativeWebRequest webRequest) {
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        String jsonBody = (String) servletRequest.getAttribute(JSONBODYATTRIBUTE);
        if (jsonBody == null) {
            try {
                String body = IOUtils.toString(servletRequest.getInputStream());
                servletRequest.setAttribute(JSONBODYATTRIBUTE, body);
                return body;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return "";
    }
}
