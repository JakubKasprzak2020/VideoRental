package pl.VideoRental.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="order_table")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private BigDecimal cost;

    @ManyToOne
    //@JsonManagedReference
    private User user;

    @OneToMany(fetch=FetchType.EAGER)
    private List<Copy> copies = new ArrayList<>();

    @OneToOne(mappedBy = "order", orphanRemoval = true) //TODO after deleting order there should be no delivery left - check is it work
    @JsonBackReference
    private Delivery delivery;



}
