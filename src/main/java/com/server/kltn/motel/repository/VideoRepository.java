package com.server.kltn.motel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.server.kltn.motel.entity.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long>{
	@Modifying(clearAutomatically = true)
	@Query("DELETE FROM Video v where v.post.id = :postId")
	void deletedVideosOfPost(@Param("postId") long postId);
}
