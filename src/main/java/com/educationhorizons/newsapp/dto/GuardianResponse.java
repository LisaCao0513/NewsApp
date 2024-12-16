package com.educationhorizons.newsapp.dto;

import com.educationhorizons.newsapp.entity.MultipleNewsSummary;
import com.educationhorizons.newsapp.entity.NewsSummary;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Data
public class GuardianResponse {
    private Response response;

    public List<MultipleNewsSummary> getSummaryArticles(String source) {
        if (isValidResponse()) {
            return Collections.emptyList();
        }
        List<MultipleNewsSummary> summaries = new ArrayList<>();
        for (GuardianNewsArticle article : response.getResults()) {
            summaries.add(
                MultipleNewsSummary.builder()
                    .title(article.getWebTitle())
                    .newsSource(source)
                    .section(article.getSectionName())
                    .link(article.getWebUrl())
                    .publicationDate(article.getWebPublicationDate())
                    .build());
        }
        return summaries;
    }

    public NewsSummary getSingleArticle(String source) {
        if (isValidResponse()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found");
        }
        GuardianNewsArticle article = response.content;
        return new NewsSummary(
            article.getWebTitle(),
            source,
            article.getSectionName(),
            article.getWebUrl(),
            article.getWebPublicationDate(),
            null,
            false
        );
    }

    private boolean isValidResponse() {
        return response == null || (response.getResults() == null && response.getContent() == null);
    }

    @Data
    static class Response {
        private List<GuardianNewsArticle> results;
        private GuardianNewsArticle content;
    }
}
