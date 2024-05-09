package com.news.newsapi.controller;

import com.news.newsapi.entity.News;
import com.news.newsapi.service.NewsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NewsControllerTest {
    @Mock
    private NewsService newsService;

    @InjectMocks
    private NewsController newsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(authorities = "USER_ROLES")
    void testGetAllNews() {
        List<News> newsList = List.of(new News(), new News());
        when(newsService.findAll()).thenReturn(newsList);

        assertEquals(newsList, newsController.getAllNews());
    }

    @Test
    @WithMockUser(authorities = "USER_ROLES")
    void testUpdateNews() {
        News news = new News();
        when(newsService.update(news, 1)).thenReturn(news);

        assertEquals(news, newsController.updateNews(news, 1));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN_ROLES", "USER_ROLES"})
    void testSaveNews() {
        News news = new News();
        when(newsService.save(news)).thenReturn(news);

        assertEquals(news, newsController.saveNews(news));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN_ROLES", "USER_ROLES"})
    void testDeleteNews() {
        newsController.deleteNews(1);

        verify(newsService).deleteById(1);
    }
}
