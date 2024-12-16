package com.educationhorizons.newsapp.controller;

import com.educationhorizons.newsapp.entity.MultipleNewsSummary;
import com.educationhorizons.newsapp.entity.NewsSummary;
import com.educationhorizons.newsapp.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/news")
@Tag(name = "News Aggregation API", description = "API endpoints for aggregating news stories and articles")
class NewsAppController {

    private final NewsService newsService;

    NewsAppController(NewsService newsService) {
        this.newsService = newsService;
    }

    @Operation(summary = "Fetch stories by query", description = "Returns stories from all sources")
    @GetMapping("/stories")
    public Map<String, List<MultipleNewsSummary>> getStories(
        @RequestParam String query
    ) {
        return newsService.fetchStories(query);
    }

    @Operation(summary = "Fetch single story details", description = "Returns details of a specific story by ID")
    @GetMapping("/story")
    public NewsSummary getStory(@RequestParam String id) {
        return newsService.fetchStoryDetail(id);
    }

}
