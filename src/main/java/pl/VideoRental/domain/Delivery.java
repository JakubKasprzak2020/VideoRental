package pl.VideoRental.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String address;

    @ManyToOne
   // @JoinColumn(name = "userId")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    private Order order;

    @OneToMany(mappedBy = "delivery", orphanRemoval = true, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Copy> copies;

}
