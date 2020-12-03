package pl.VideoRental.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Copy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
 //   @JoinColumn(name = "movieId")
    private Movie movie;

    @ManyToOne
  //  @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
 //   @JoinColumn(name = "cartId")
    private Cart cart;

    @ManyToOne
  //  @JoinColumn(name = "orderId")
    private Order order;

    @ManyToOne
  //  @JoinColumn(name = "deliveryId")
    private Delivery delivery;


}
