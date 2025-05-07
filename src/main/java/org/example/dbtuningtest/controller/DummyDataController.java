package org.example.dbtuningtest.controller;

import lombok.RequiredArgsConstructor;
import org.example.dbtuningtest.service.DummyDataService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dummy")
public class DummyDataController {

    private final DummyDataService dummyDataService;

    @PostMapping("/members")
    public String insertMembers() {
        dummyDataService.insertDummyData();
        return "Members Inserted";
    }

    @PostMapping("/posts")
    public String insertPosts() {
        dummyDataService.insertDummyPosts();
        return "Posts Inserted";
    }
}

