package com.server.kltn.motel.api.user.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.server.kltn.motel.api.user.payload.PostDetailPayload;
import com.server.kltn.motel.service.PostDetailService;

@RestController
@RequestMapping("/api")
public class PostDetailController {
	
	@Autowired
	private PostDetailService postDetailService;
	
	@GetMapping(value = "/auth/get-post-detail/{postId}")
	public ResponseEntity<PostDetailPayload> 
		getTotalNewsOfProvince(@PathVariable(value = "postId") long postId){
		PostDetailPayload postDetailPayload = postDetailService.getPostDetail(postId);
		return new ResponseEntity<PostDetailPayload>(postDetailPayload, HttpStatus.OK);
	}
}
