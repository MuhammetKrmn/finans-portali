package com.finansportal.backend.repository;

import com.finansportal.backend.entity.NewsArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public interface NewsArticleRepository
        extends JpaRepository<NewsArticle, UUID> {
    Page<NewsArticle>findByCategoryOrderByPublishedAtDesc(
            String category, Pageable pageable
            );
    Page<NewsArticle> findAllByOrderByPublishedAtDesc(Pageable pageable);
    boolean existsByUrl(String url);
    int deleteByFetchedAtBefore(LocalDateTime cutoff);
}
