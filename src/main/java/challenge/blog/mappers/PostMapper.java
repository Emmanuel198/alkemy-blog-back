package challenge.blog.mappers;

import challenge.blog.controller.request.PostRequest;
import challenge.blog.controller.response.PostFullResponse;
import challenge.blog.controller.response.PostResponse;
import challenge.blog.entity.PostEntity;
import challenge.blog.models.Paginated;
import challenge.blog.models.Post;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostMapper {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private DateTimeFormatter dateTimeFormatter;

    public PostEntity map(Post post) {
        return modelMapper.map(post, PostEntity.class);
    }

    public Post map(PostRequest postRequest) {
        Post post = new Post();
        post.setCategory(postRequest.getCategory());
        post.setContent(postRequest.getContent());
        post.setImage(postRequest.getImage());
        post.setTitle(postRequest.getTitle());
        return post;
    }

    public PostResponse mapToResponse(Post post){
        return modelMapper.map(post, PostResponse.class);
    }

    public Post map(PostEntity postEntity) {
        return modelMapper.map(postEntity, Post.class);
    }

    public PostFullResponse mapToPostResponse(Post post) {
        return modelMapper.map(post, PostFullResponse.class);
    }

    public List<Post> map(List<PostEntity> postEntities) {
        List<Post> posts = new ArrayList<>();
        for (PostEntity postEntity : postEntities) {
            posts.add(map(postEntity));
        }
        return posts;
    }

    public List<PostResponse> mapToResponse(List<Post> posts) {
        List<PostResponse> postResponses = new ArrayList<>();
        for (Post post : posts) {
            postResponses.add(mapToResponse(post));
        }
        return postResponses;
    }

    public Paginated mapToResponse (Paginated paginated){
        paginated.setElements(mapToResponse(paginated.getElements()));
        return paginated;
    }
}
