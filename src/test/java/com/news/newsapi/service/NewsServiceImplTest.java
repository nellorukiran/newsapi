package com.news.newsapi.service;

import com.news.newsapi.entity.News;
import com.news.newsapi.repository.NewsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NewsServiceImplTest {
    @Mock
    private NewsRepository newsRepository;

    @InjectMocks
    private NewsServiceImpl newsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        List<News> newsList = List.of(new News(), new News());
        when(newsRepository.findAll()).thenReturn(newsList);

        assertEquals(newsList, newsService.findAll());
    }

    @Test
    void testFindById() {
        int newsId = 1;
        News news = new News();
        news.setId(newsId);
        when(newsRepository.findById(newsId)).thenReturn(Optional.of(news));

        assertEquals(news, newsService.findById(newsId));
    }

    @Test
    void testSave() {
        News news = new News();
        when(newsRepository.save(news)).thenReturn(news);

        assertEquals(news, newsService.save(news));
    }

    @Test
    void testDeleteById() {
        int newsId = 1;
        newsService.deleteById(newsId);

        verify(newsRepository).deleteById(newsId);
    }

    @Test
    void testUpdate() {
        int newsId = 1;
        News existingNews = new News();
        existingNews.setId(newsId);

        News updatedNews = new News();
        updatedNews.setId(newsId);
        updatedNews.setTitle("Updated Title");

        when(newsRepository.findById(newsId)).thenReturn(Optional.of(existingNews));
        when(newsRepository.save(updatedNews)).thenReturn(updatedNews);

        assertEquals(updatedNews, newsService.update(updatedNews, newsId));
    }
}
