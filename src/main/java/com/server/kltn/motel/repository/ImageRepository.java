package com.server.kltn.motel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.server.kltn.motel.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>{
	@Modifying(clearAutomatically = true)
	@Query("DELETE FROM Image i where i.post.id = :postId")
	void deletedImagesOfPost(@Param("postId") long postId);
}
