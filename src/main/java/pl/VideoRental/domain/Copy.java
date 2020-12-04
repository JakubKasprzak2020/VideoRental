package pl.VideoRental.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Copy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private LocalDate rentDate;

    private int rentalDays;

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
