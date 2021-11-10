package com.dataart.javaschool.news.repo;

import com.dataart.javaschool.news.models.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findByOrderByDateDesc();
}

