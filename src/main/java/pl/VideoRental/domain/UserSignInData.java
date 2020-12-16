package pl.VideoRental.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignInData {

    private String name;
    private String lastName;
    private String password;
    private String email;
    private String address;

}
