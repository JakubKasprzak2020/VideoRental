package pl.VideoRental.domain;

import lombok.*;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
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
  //  @JoinColumn(name = "fk_user")
    private User user;

    @OneToMany(mappedBy = "order")
    private List<Copy> copies = new ArrayList<>();

    @OneToOne(mappedBy = "order")
    private Delivery delivery;

}
