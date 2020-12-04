package pl.VideoRental.useCase.port;


import org.springframework.stereotype.Component;
import pl.VideoRental.domain.Copy;

@Component
public class IsCopyFree {


    public boolean isFree(Copy copy){
        return copy.getUser() == null;
    }

}
