package com.example.pifinance_back.Services;

import com.example.pifinance_back.Entities.Client;
import com.example.pifinance_back.Entities.Topic;
import com.example.pifinance_back.Entities.TopicDTO;
import com.example.pifinance_back.Repositories.ClientRepository;
import com.example.pifinance_back.Repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService implements ITopicService{

    @Autowired
    TopicRepository topicRepo;
    @Autowired
    ClientRepository userRepo;

   /* @Override
    public List<Topic> getAllTopics() {
        return (List<Topic>) topicRepo.findAll();
    }*/

    @Override
    public List<TopicDTO> getAllTopics() {
        Iterable<Topic> topicsIterable = topicRepo.findAll();
        List<Topic> topics = new ArrayList<>();
        topicsIterable.forEach(topics::add);
        return topics.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private TopicDTO convertToDto(Topic topic) {
        TopicDTO topicDTO = new TopicDTO();
        topicDTO.setIdTopic(topic.getIdTopic());
        topicDTO.setTitle(topic.getTitle());
        topicDTO.setQuestion(topic.getQuestion());
        topicDTO.setLikes(topic.getLikes());
        topicDTO.setDislikes(topic.getDislikes());
        topicDTO.setCreationDate(topic.getCreationDate());
        // Ajoutez l'ID de l'utilisateur
        if (topic.getUser() != null) {
            topicDTO.setUserId(topic.getUser().getId());
        }
        return topicDTO;
    }

    @Override
    public Topic addTopic(Topic topic, Long idUser) {
        Client user = userRepo.findById(idUser).orElse(null);
        topic.setUser(user);

        topic.setCreationDate(new Date());
        topic.setLikes(0);
        topic.setDislikes(0);

        return topicRepo.save(topic);
    }

    @Override
    public void deleteTopic(Long topicId) {
        topicRepo.deleteById(topicId);
    }

    /*@Override
    public Topic getTopic(Long topicId) {
        return topicRepo.findById(topicId).orElse(null);
    }*/

    @Override
    public TopicDTO getTopic(Long topicId) {
        Topic topic = topicRepo.findById(topicId).orElse(null);

        if (topic != null) {
            return convertToDto(topic);
        }

        return null;
    }




    @Override
    public Long countPostsByIdTopic(Long idTopic) {
        return topicRepo.countPostsByIdTopic(idTopic);
    }



}
