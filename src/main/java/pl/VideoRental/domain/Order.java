package pl.VideoRental.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="userOrder")
public class Order {

    @Id
    @GeneratedValue
    private long id;

    private BigDecimal cost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany (fetch = FetchType.LAZY, mappedBy = "order")
    private List<Copy> copies;

    @OneToOne(cascade = CascadeType.ALL)
    private Delivery delivery;

}
