package challenge.blog.controller;

import challenge.blog.controller.request.PostRequest;
import challenge.blog.controller.response.PostFullResponse;
import challenge.blog.controller.response.PostResponse;
import challenge.blog.exceptions.PostNotFound;
import challenge.blog.mappers.PostMapper;
import challenge.blog.models.Paginated;
import challenge.blog.models.Post;
import challenge.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
@RequestMapping("/posts")
@CrossOrigin("*")
public class PostController {

    @Autowired
    PostMapper postMapper;
    @Autowired
    PostService postService;
    @Autowired
    private HttpServletRequest request;

    @GetMapping
    public ResponseEntity<Paginated> getAllPost(@RequestParam(required = false) Integer page,
                                                @RequestParam(required = false) Integer pageSize) {
        return ResponseEntity.ok(postMapper.mapToResponse(postService.getAllPost(page, pageSize)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostFullResponse> getPostById(@PathVariable("id") Long id) throws PostNotFound {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(postMapper.mapToPostResponse(post));
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest) {
        Long postId = postService.createPost(postMapper.map(postRequest));
        return ResponseEntity.created(URI.create(request.getRequestURL().toString() + "/" + postId)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePostById(@PathVariable("id") Long id) throws PostNotFound {
        postService.deletePostById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostFullResponse> updatePost
            (@PathVariable Long id, @RequestBody PostRequest postRequest) throws PostNotFound {
        postService.updatePost(postMapper.map(postRequest), id);
        return ResponseEntity.ok().build();
    }

}
