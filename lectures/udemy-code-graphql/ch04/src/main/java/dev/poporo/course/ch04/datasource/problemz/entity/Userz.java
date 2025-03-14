package dev.poporo.course.ch04.datasource.problemz.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "userz")
@Setter
@Getter
public class Userz {

    @Id
    private UUID id;
    private String username;
    private String email;
    private String hashedPassword;
    private URL avatar;

    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    private String displayName;
    private boolean active;
}
