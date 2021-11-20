package jooom.simple_batch.User;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Item {

    @Id @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public void setUser(User user){
        if (this.user == null){
            this.user = user;
            this.user.addItem(this);
        }
    }
}
