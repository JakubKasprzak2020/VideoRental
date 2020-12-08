package pl.VideoRental.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    BigDecimal toPay = BigDecimal.ZERO;

    @OneToMany //(mappedBy = "cart", fetch = FetchType.EAGER)
    private List<Copy> copies = new ArrayList<>();

}
