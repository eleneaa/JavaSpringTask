package com.example.mod4;

import com.example.mod4.model.Category;
import com.example.mod4.model.Comment;
import com.example.mod4.model.News;
import com.example.mod4.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.ArrayList;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class AbstractTestController {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    protected User createUser(Long id, Comment comment, News news) {

        User user = new User(id,"Name " + id,"Name"+id+"@mail.ru",new ArrayList<>(),new ArrayList<>());
        if (comment != null){
            user.addComment(comment);
        }
        if (news != null){
            user.addNews(news);
        }
        return user;
    }
    protected Comment createComment(Long id, User user, News news) {
        return new Comment(id,"comment" +id, Instant.now(),Instant.now(),user, news);
    }
    protected News createNews(Long id, Category category, User user) {
        return new News(id,"tittle" + id,"description" + id,"body" + id,
                Instant.now(),Instant.now(),category,user,new ArrayList<>());
    }
    protected Category createCategory(Long id) {
        return new Category(id,"name" + id,new ArrayList<>());
    }


}
