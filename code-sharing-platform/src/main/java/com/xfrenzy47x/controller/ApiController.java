package com.xfrenzy47x.controller;

import com.xfrenzy47x.dto.NewCodeRequestDto;
import com.xfrenzy47x.dto.NewCodeResponseDto;
import com.xfrenzy47x.model.CodePlatformPage;
import com.xfrenzy47x.service.CodePlatformPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ApiController {
    Logger logger = Logger.getLogger(ApiController.class.getName());

    @Autowired
    CodePlatformPageService pageService;

    @GetMapping("/api/code/{id}")
    public ResponseEntity<Object> getApiCode(@PathVariable(required = false) String id) {
        logger.log(Level.INFO,"getApiCode() : {0}", id);
        CodePlatformPage page = pageService.get(id);

        if (page == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(page);
    }

    @GetMapping("/api/code/latest")
    public  ResponseEntity<Object> getLatestApiCodes() {
        logger.log(Level.INFO,"getLatestApiCodes()");
        return ResponseEntity.ok(pageService.getLatest());
    }

    @PostMapping("/api/code/new")
    public ResponseEntity<Object> saveNewCode(@RequestBody NewCodeRequestDto newCodeRequestDto) {
        logger.log(Level.INFO,"saveNewCode() : {0}", newCodeRequestDto);
        String id = pageService.add(newCodeRequestDto.getCode(), newCodeRequestDto.getTime(), newCodeRequestDto.getViews());
        return ResponseEntity.ok(new NewCodeResponseDto(id));
    }
}
