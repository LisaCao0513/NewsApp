package com.educationhorizons.newsapp.service;

import com.educationhorizons.newsapp.Strategy.NewsSourceStrategy;
import com.educationhorizons.newsapp.entity.MultipleNewsSummary;
import com.educationhorizons.newsapp.entity.NewsSummary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class NewsService {
    private final Map<String, NewsSourceStrategy> sources = new HashMap<>();

    @Autowired
    public NewsService(List<NewsSourceStrategy> strategies) {
        strategies.forEach(strategy -> sources.put(strategy.getSourceName().toLowerCase(), strategy));
    }

    public Map<String, List<MultipleNewsSummary>> fetchStories(String query) {
        return sources.values().parallelStream()
            .collect(Collectors.toConcurrentMap(
                NewsSourceStrategy::getSourceName,
                source -> source.fetchStories(query)
            ));
    }

    public NewsSummary fetchStoryDetail(String id) {
        return sources.values().parallelStream()
            .map(source -> source.fetchStoryDetail(id))
            .filter(Objects::nonNull)
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Story not found"));
    }

}
