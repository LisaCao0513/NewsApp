package com.educationhorizons.newsapp.Strategy;

import com.educationhorizons.newsapp.entity.MultipleNewsSummary;
import com.educationhorizons.newsapp.entity.NewsSummary;
import java.util.List;

public interface NewsSourceStrategy {
    String getSourceName();
    List<MultipleNewsSummary> fetchStories(String query);
    NewsSummary fetchStoryDetail(String id);
}
