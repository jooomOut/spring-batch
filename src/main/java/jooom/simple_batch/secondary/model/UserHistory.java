package jooom.simple_batch.secondary.model;

import lombok.*;
import org.apache.tomcat.jni.Local;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UserHistory {

    @Id @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    private String username;

    @Column(unique = true)
    private String email;
    private String major;

    @CreatedDate
    private LocalDate createdData;

    private Integer itemCount;



}
