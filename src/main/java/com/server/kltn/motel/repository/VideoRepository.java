package com.server.kltn.motel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.kltn.motel.entity.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long>{

}
