package com.educationhorizons.newsapp.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsSummary {
    private String title;
    private String newsSource;
    private String section;
    private String link;
    private String publicationDate;
    private String image;
    private boolean pinned;
}
