package com.example.myrestfulservice.contoller;

import com.example.myrestfulservice.bean.Author;
import com.example.myrestfulservice.bean.HelloWorldBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello World");
    }

    // http://localhost:8080/hello-world-bean/path-variable/kenneth
    @GetMapping(path = "/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloWorldBean(@PathVariable(value = "name") String name) {
        String msg = String.format("Hello World, %s", name);

        return new HelloWorldBean(msg);
    }

    // http://localhost:8080/books/{bookId}/authors/{authorId}
    @GetMapping(path ="/books/{bookId}/authors/{authorId}")
    public Author getAuthorDetailByBookId(@PathVariable(value="bookId") String bookId,
                                          @PathVariable(value="authorId") String authorId) {
       return Author.builder()
               .bookId(bookId)
               .id(authorId)
               .name("Dowon Lee")
               .build();
    }
}
