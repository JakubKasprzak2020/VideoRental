package pl.VideoRental.useCase.port.copyPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.CopyRepository;
import pl.VideoRental.adapter.repository.UserRepository;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.User;

@Component
@RequiredArgsConstructor
public class RentACopy {

    private final CopyRepository copyRepository;
    private final UserRepository userRepository;

    /**
     * There is no verification if copy is available because rent method is used to copies that are already in users cart.
     * Verification is before in this process during method add from AddCopyToCart class.
     */


   public void rent(Copy copy, User user){
       copy.setUser(user);
       copy.setAvailable(false);
       copyRepository.save(copy);
       user.getCopies().add(copy);
       userRepository.save(user);
   }

}
