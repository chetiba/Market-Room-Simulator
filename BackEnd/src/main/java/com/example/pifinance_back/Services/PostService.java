package com.example.pifinance_back.Services;

import com.example.pifinance_back.Entities.*;
import com.example.pifinance_back.Repositories.ClientRepository;
import com.example.pifinance_back.Repositories.PostRepository;
import com.example.pifinance_back.Repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService implements IPostService{

    @Autowired
    PostRepository postRepo;
    @Autowired
    ClientRepository userRepo;
    @Autowired
    TopicRepository topicRepo;


   /* @Override
    public List<Post> getAllPosts() {
        return (List<Post>) postRepo.findAll();
    }*/

    @Override
    public List<PostDTO> getAllPosts() {
        Iterable<Post> postsIterable = postRepo.findAll();
        List<Post> posts = new ArrayList<>();
        postsIterable.forEach(posts::add);
        return posts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> getPostsByTopicId(Long idTopic) {
        List<Post> posts = postRepo.findByTopicId(idTopic);
        return posts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private PostDTO convertToDto(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setIdPost(post.getIdPost());
        postDTO.setContent(post.getContent());
        postDTO.setLikes(post.getLikes());
        postDTO.setDislikes(post.getDislikes());
        postDTO.setCreationDate(post.getCreationDate());
        postDTO.setModified(post.getModified());
        // Ajoutez l'ID de l'utilisateur
        if (post.getUser() != null) {
            postDTO.setUserId(post.getUser().getId());
        }
        return postDTO;
    }

    @Override
    public Post getPost(Long postId) {
        return postRepo.findById(postId).orElse(null);
    }

    @Override
    public Post addPost(Post post, Long idUser, Long idTopic) {
        Client user = userRepo.findById(idUser).orElse(null);
        Topic topic = topicRepo.findById(idTopic).orElse(null);
        post.setUser(user);
        post.setTopic(topic);

        post.setCreationDate(new Date());
        post.setLikes(0);
        post.setDislikes(0);
        post.setModified(false);


        return postRepo.save(post);
    }

    @Override
    public Post updatePost(Post post) {
        if (postRepo.existsById(post.getIdPost())) {
            Post postExist = postRepo.findById(post.getIdPost()).orElse(null);
            if (postExist != null) {
                postExist.setContent(post.getContent());
                postExist.setCreationDate(new Date());
                postExist.setModified(true);

                return postRepo.save(postExist);
            }
        }
        return null;
    }

    @Override
    public void deletePost(Long postId) {

        postRepo.deleteById(postId);
    }


    /*@Override
    public List<Post> getPostsByTopicId(Long idTopic) {

        return (List<Post>) postRepo.findByTopicId(idTopic);
    }*/

    @Override
    public Long countCommentsByIdPost(Long idPost) {
        return postRepo.countCommentsByIdPost(idPost);
    }

}
