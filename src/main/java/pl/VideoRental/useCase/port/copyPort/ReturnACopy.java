package pl.VideoRental.useCase.port.copyPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.CopyRepository;
import pl.VideoRental.adapter.repository.UserRepository;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.exception.CopyIsNotRentedException;

@Component
@RequiredArgsConstructor
public class ReturnACopy {

    private final UserRepository userRepository;
    private final CopyRepository copyRepository;

   public void returnACopy(Copy copy) throws CopyIsNotRentedException {
       if (copy.getUser()==null){
           throw new CopyIsNotRentedException(copy.getId());
       }
       User user = copy.getUser();
       user.getCopies().remove(copy);
       copy.setUser(null);
       copy.setAvailable(true);
       copy.setRentalDays(0);
       copy.setRentalDate(null);
       userRepository.save(user);
       copyRepository.save(copy);
    }
}
