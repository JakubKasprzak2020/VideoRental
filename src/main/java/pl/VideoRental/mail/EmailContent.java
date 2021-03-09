package pl.VideoRental.mail;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailContent {

    private String subject;
    private String text;

}
