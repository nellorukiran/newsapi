package com.news.newsapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Add @Entity annotation to the class to mark it as an entity class
@Entity
@Data
//Add @NoArgsConstructor annotation to generate a default constructor
@NoArgsConstructor
//Add @AllArgsConstructor annotation to generate a constructor with all fields as arguments
@AllArgsConstructor
@Builder
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    private String content;
    private String author;
    private String category;
    public Object thenReturn(News news) {
        return news;
    }
}
