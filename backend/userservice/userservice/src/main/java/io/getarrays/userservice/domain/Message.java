package io.getarrays.userservice.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String senderName;
    private String receiverName;
    private String message;
    private String date;
}
