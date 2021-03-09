package pl.VideoRental.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.Order;
import pl.VideoRental.domain.User;

@Component
@RequiredArgsConstructor
public class EmailContentCreator {

    public EmailContent getContentForUserTypePromotion(User user){
        String userType = user.getUserType().name();
        String subject = "Congratulations!";
        String text = String.format("Congratulations on your new %s user type!", userType);
        return EmailContent.builder()
                .subject(subject)
                .text(text)
                .build();
    }

    public EmailContent getContentForOrderConfirmation(Order order){
        String subject = "Order confirmation";
        String movieTitles = getMovieTitles(order);
        String text = String.format(
                "Order number %s was placed. You have ordered: \n" +
                movieTitles +
                "The cost of the order is " + order.getCost(), order.getId());
        return EmailContent.builder()
                .subject(subject)
                .text(text)
                .build();
    }

    public EmailContent getContentForThanksForGivingTheMovieBack(String movieTitle){
        String subject = "Thank you!";
        String text = String.format("Thank you for giving the %s movie back", movieTitle);
        return EmailContent.builder()
                .subject(subject)
                .text(text)
                .build();
    }

    private String getMovieTitles(Order order){
        StringBuilder sb = new StringBuilder();
        for (Copy copy : order.getCopies()){
            sb.append(copy.getMovie().getTitle() + "\n");
        }
        return sb.toString();
    }




}
