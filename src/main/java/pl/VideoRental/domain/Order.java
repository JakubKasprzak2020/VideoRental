package pl.VideoRental.domain;

import lombok.*;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="order_table")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private BigDecimal cost;

    @ManyToOne
 //   @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "order", orphanRemoval = true, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Copy> copies;

    @OneToOne(cascade = CascadeType.ALL)
    private Delivery delivery;

}
