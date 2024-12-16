package com.educationhorizons.newsapp.Strategy;

import com.educationhorizons.newsapp.dto.GuardianResponse;
import com.educationhorizons.newsapp.entity.MultipleNewsSummary;
import com.educationhorizons.newsapp.entity.NewsSummary;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class GuardianNewsSource implements NewsSourceStrategy {
    private static final String BASE_URL = "https://content.guardianapis.com";
    @Value("${guardian.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate;

    public GuardianNewsSource(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String getSourceName() {
        return "Guardian News";
    }

    @Override
    public List<MultipleNewsSummary> fetchStories(String query) {
        String url = String.format("%s/search?q=%s&api-key=%s", BASE_URL, query, apiKey);
        try {
            GuardianResponse response = restTemplate.getForObject(url, GuardianResponse.class);
            return Optional.ofNullable(response)
                .map(res -> res.getSummaryArticles(getSourceName()))
                .orElse(Collections.emptyList());
        } catch (RestClientException e) {
            // Handle any other RestClientException (e.g., network issues)
            System.err.println("Error occurred while making the request: " + e.getMessage());
            return Collections.emptyList();  // Return an empty list in case of error
        } catch (Exception e) {
            // Handle other general exceptions
            System.err.println("An unexpected error occurred: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public NewsSummary fetchStoryDetail(String id) {
        String url = String.format("%s/%s?api-key=%s", BASE_URL, id, apiKey);
        try {
            GuardianResponse response = restTemplate.getForObject(url, GuardianResponse.class);
            return Optional.ofNullable(response).map(res -> res.getSingleArticle(getSourceName())).orElse(null);
        } catch (RestClientException e) {
            // Handle any other RestClientException (e.g., network issues)
            System.err.println("Error occurred while making the request: " + e.getMessage());
            return null;  // Return null in case of error
        } catch (Exception e) {
            // Handle other general exceptions
            System.err.println("An unexpected error occurred: " + e.getMessage());
            return null;
        }
    }
}
