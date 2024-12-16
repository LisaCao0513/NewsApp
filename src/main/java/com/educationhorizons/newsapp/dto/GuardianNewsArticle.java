package com.educationhorizons.newsapp.dto;

import lombok.Data;

//dto from results in response
@Data
public class GuardianNewsArticle {
    private final String id;
    private final String webUrl;
    private final String webTitle;
    private final String sectionName;
    private final String webPublicationDate;
}
