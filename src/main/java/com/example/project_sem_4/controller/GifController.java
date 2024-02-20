package com.example.project_sem_4.controller;

import com.example.project_sem_4.entity.Gif;
import com.example.project_sem_4.entity.User;
import com.example.project_sem_4.service.GifService;
import com.example.project_sem_4.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("api/gifs")
@Tag(name = "Gif", description = "Gif management APIs")
public class GifController {

    private final GifService gifService;
    private final UserService userService;


    public GifController(GifService gifService, UserService userService) {
        this.gifService = gifService;
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<LinkedHashMap<String, Object>> uploadGif(@RequestParam("gifFile") MultipartFile gifFile, Authentication authentication, @RequestParam("title") String title) throws IOException {
        User user = userService.findByEmail(authentication.getName());
        String url = gifService.uploadFile(gifFile);
        gifService.saveGifToDB(url, title , user);

        // I am Think about proper error handling in the future that's why it takes status
        LinkedHashMap<String, Object> jsonResponse = gifService.modifyJsonResponse("create", url);
        return new ResponseEntity<>(jsonResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{gifId}")
    public ResponseEntity<?> deleteGif(@PathVariable Long gifId, Authentication authentication){
        User currentUser = userService.findByEmail(authentication.getName());
        Gif gif = gifService.findGifByIdAndUser(gifId, currentUser);
        if(gif == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        gifService.deleteGif(gif, currentUser);
        return ResponseEntity.ok("delete success");
    }

    @GetMapping("")
    public ResponseEntity<?> getGif(Authentication authentication){
        User currentUser = userService.findByEmail(authentication.getName());
        List<Gif> gifs = gifService.findGifByUser(currentUser);
        if(gifs.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(gifs, HttpStatus.ACCEPTED);
    }


    @GetMapping("/{gifId}")
    public ResponseEntity<?> getASpecificGif(@PathVariable Long gifId){
        Gif gif = gifService.findGifById(gifId);
        if(gif == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(gif, HttpStatus.OK);
    }
}
