package com.bryan.api.rest.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bryan.api.rest.app.entity.vm.Asset;
import com.bryan.api.rest.app.service.S3Service;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

	@Autowired
	private S3Service s3Service;

	@PostMapping(value = "/upload", consumes = { "multipart/form-data" })
	Map<String, String> upload(@RequestParam("file") @RequestPart MultipartFile file) {

		String key = s3Service.putObjetct(file);

		Map<String, String> result = new HashMap<>();
		result.put("key", key);
		result.put("url", s3Service.getObjectUrl(key));

		return result;

	}

	@GetMapping(value = "/get-object", params = "key")
	ResponseEntity<ByteArrayResource> getObject(@RequestParam String key) {

		Asset asset = s3Service.getObject(key);
		ByteArrayResource resource = new ByteArrayResource(asset.getContent());

		return ResponseEntity.ok().header("Content-Type", asset.getContentType())
				.contentLength(asset.getContent().length).body(resource);

	}

	@DeleteMapping(value = "/delete-object", params = "key")
	void deleteObject(@RequestParam String key) {

		s3Service.deleteObject(key);
	}

}