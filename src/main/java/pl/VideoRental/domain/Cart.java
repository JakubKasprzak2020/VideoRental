package pl.VideoRental.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cart {

    @Id
    @GeneratedValue
    private long id;

    private BigDecimal cost;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cart")
    private List<Copy> copies;

    @OneToOne(cascade = CascadeType.ALL)
    private Order order;




}
