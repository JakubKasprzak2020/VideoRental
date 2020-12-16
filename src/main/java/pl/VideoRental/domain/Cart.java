package pl.VideoRental.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
@RequiredArgsConstructor
@Getter
@Setter
public class Cart {

    private BigDecimal toPay = BigDecimal.ZERO;

    private List<Copy> copies = new ArrayList<>();




}



/*package pl.VideoRental.domain;

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

}*/
