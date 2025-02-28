package com.example.pifinance_back.Entities;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TopicDTO implements Serializable {
    private Long idTopic;
    private String title;
    private String question;
    private Integer likes;

    private Integer dislikes;
    private Date creationDate;
    private Long userId;
}

