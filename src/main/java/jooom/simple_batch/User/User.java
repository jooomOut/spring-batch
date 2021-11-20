package jooom.simple_batch.User;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class User {

    @Id @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    private String username;

    @Column(unique = true)
    private String email;
    private String major;

}
