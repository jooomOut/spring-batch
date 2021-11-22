package jooom.simple_batch.primary.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class User {

    @Id @GeneratedValue
    @Column(name = "USER_ID", nullable = false)
    private Long id;

    private String username;

    @Column(unique = true)
    private String email;
    private String major;

    @Builder.Default
    private Integer count = 0;

    // 한 번에 생성하고 저장하는 경우, cascade 추가 안하면 item 이 저장되지 않는다.
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        if (!items.contains(item)){
            items.add(item);
            item.setUser(this);
        }
    }
}
