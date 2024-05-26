package org.example.model;

import lombok.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
    private Long client_id;
    private LocalDateTime deliveredTime;

    public Message(String text, Long client_id) {
        this.text = text;
        this.client_id = client_id;
    }

    /*
        Не бачу зараз сенсу писати поле Статус, так як воно повністю залежить від наявності деліверед тайм,
        але коли мова йде про реальний проект то там звісно потрібно враховувати ефект маштабованості та
        розраховувати на те що статусів може стати більше і ця залежність втратить сенс.
     */
}
