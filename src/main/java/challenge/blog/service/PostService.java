package challenge.blog.service;

import challenge.blog.controller.response.PostResponse;
import challenge.blog.entity.PostEntity;
import challenge.blog.exceptions.PostNotFound;
import challenge.blog.mappers.PostMapper;
import challenge.blog.models.Paginated;
import challenge.blog.models.Post;
import challenge.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    PostMapper postMapper;
    @Autowired
    PostRepository postRepository;


    public Paginated getAllPost(Integer page, Integer pageSize) {
        if (pageSize == null) {
            pageSize = 10;
        }
        if (page == null) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("date").descending());

        Page<PostEntity> postEntities = postRepository.findAll(pageable);

        Paginated<Post> paginated = new Paginated<>();
        paginated.setPage(page);
        paginated.setPageSize(pageSize);
        paginated.setTotalPages(postEntities.getTotalPages());
        paginated.setTotalElements(postEntities.getTotalElements());
        paginated.setElements(postMapper.map(postEntities.getContent()));

        return paginated;
    }

    public Post getPostById(Long id) throws PostNotFound {
        Optional<PostEntity> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new PostNotFound();
        }
        return postMapper.map(post.get());
    }

    public Long createPost(Post post) {
        post.setDate(LocalDateTime.now());
        PostEntity createdPostEntity = postRepository.save(postMapper.map(post));
        return createdPostEntity.getId();
    }
    public void deletePostById(Long id) throws PostNotFound {
        Optional<PostEntity> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new PostNotFound();
        }
        postRepository.deleteById(id);
    }
    public void updatePost(Post post, Long id) throws PostNotFound {
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);
        if (post.getCategory() != null) {
            postEntity.setCategory(post.getCategory());
        }
        if (post.getContent() != null) {
            postEntity.setContent(post.getContent());
        }
        if (post.getDate() != null) {
            postEntity.setDate(post.getDate());
        }
        if (post.getImage() != null) {
            postEntity.setImage(post.getImage());
        }
        if (post.getTitle() != null) {
            postEntity.setTitle(post.getTitle());
        }
        postRepository.save(postEntity);
    }



}
