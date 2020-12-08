package pl.VideoRental.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private BigDecimal toPay = BigDecimal.ZERO;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @OneToMany(mappedBy = "cart", orphanRemoval = true, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Copy> copies;




}
