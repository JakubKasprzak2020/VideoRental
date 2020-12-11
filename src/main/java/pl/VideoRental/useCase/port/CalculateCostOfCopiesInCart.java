package pl.VideoRental.useCase.port;

import org.springframework.stereotype.Component;
import pl.VideoRental.domain.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Component
public class CalculateCostOfCopiesInCart {


     final BigDecimal BASIC_COST_FOR_ONE_DAY = BigDecimal.valueOf(5);

     final BigDecimal IMPACT_ON_THE_PRICE_OF_PREMIERE_MOVIE = BigDecimal.valueOf(3);
     final BigDecimal IMPACT_ON_THE_PRICE_OF_NEW_MOVIE = BigDecimal.valueOf(2);
     final BigDecimal IMPACT_ON_THE_PRICE_OF_STANDARD_MOVIE = BigDecimal.valueOf(1);
     final BigDecimal IMPACT_ON_THE_PRICE_OF_CLASSIC_MOVIE = BigDecimal.valueOf(0.5);

     final BigDecimal IMPACT_ON_THE_PRICE_OF_SHORT_TERM_RENTAL = BigDecimal.valueOf(2);
     final BigDecimal IMPACT_ON_THE_PRICE_OF_STANDARD_TERM_RENTAL = BigDecimal.valueOf(1);
     final BigDecimal IMPACT_ON_THE_PRICE_OF_LONG_TERM_RENTAL = BigDecimal.valueOf(0.5);

     final BigDecimal IMPACT_ON_THE_PRICE_OF_SILVER_USERTYPE = BigDecimal.valueOf(0.95);;
     final BigDecimal IMPACT_ON_THE_PRICE_OF_GOLD_USERTYPE = BigDecimal.valueOf(0.85);
     final BigDecimal IMPACT_ON_THE_PRICE_OF_PLATINUM_USERTYPE = BigDecimal.valueOf(0.7);

     final int MAX_DAYS_FOR_SHORT_TERM_RENTAL = 3;
     final int MAX_DAYS_FOR_STANDARD_TERM_RENTAL = 6;

    public void calculate(User user) {
        Cart cart = user.getCart();
       List<Copy> copies = cart.getCopies();
       for (Copy c : copies) {
           BigDecimal costOfCopy = calculateCostOfCopy(c, user);
           BigDecimal currentToPay = cart.getToPay();
           cart.setToPay(currentToPay.add(costOfCopy));
       }
    }


    BigDecimal calculateCostOfCopy(Copy copy, User user) {
        int rentalDays = copy.getRentalDays();
        LocalDate rentalDate = copy.getRentalDate();
        BigDecimal basicCost = calculateBasicCost(rentalDays);
        BigDecimal impactOfDateRelease = getImpactOfDateRelease(copy, rentalDate);
        BigDecimal impactOfUserType = getImpactOfUserType(user);
        return basicCost.multiply(impactOfDateRelease).multiply(impactOfUserType);
    }


     int howManyDaysPassedFromRelease(Copy copy, LocalDate rentDate) {
        Movie movie = copy.getMovie();
        LocalDate releaseDate = movie.getReleaseDate();
        int days = Period.between(releaseDate, rentDate).getDays();
        return days;
    }

     BigDecimal getImpactOfDateRelease(Copy copy, LocalDate rentDate) {
        int daysFromRelease = howManyDaysPassedFromRelease(copy, rentDate);
        if (daysFromRelease <= 14) {
            return IMPACT_ON_THE_PRICE_OF_PREMIERE_MOVIE;
        } else if (daysFromRelease <= 90) {
            return IMPACT_ON_THE_PRICE_OF_NEW_MOVIE;
        } else if (daysFromRelease <= 365) {
            return IMPACT_ON_THE_PRICE_OF_STANDARD_MOVIE;
        } else {
            return IMPACT_ON_THE_PRICE_OF_CLASSIC_MOVIE;
        }
    }

     BigDecimal getImpactOfUserType(User user) {
        if (user.getUserType()==null){
            return BigDecimal.ONE;
        }
        UserType userType = user.getUserType();
        if (userType == UserType.PLATINUM) {
            return IMPACT_ON_THE_PRICE_OF_PLATINUM_USERTYPE;
        } else if (userType == UserType.GOLD) {
            return IMPACT_ON_THE_PRICE_OF_GOLD_USERTYPE;
        } else if (userType == UserType.SILVER) {
            return IMPACT_ON_THE_PRICE_OF_SILVER_USERTYPE;
        } else {
            return BigDecimal.ONE;
        }
    }

     BigDecimal calculateBasicCostForShortTerm(int days){
        BigDecimal rentalDays = BigDecimal.valueOf(days);
        return BASIC_COST_FOR_ONE_DAY.multiply(rentalDays).multiply(IMPACT_ON_THE_PRICE_OF_SHORT_TERM_RENTAL);
    }

     BigDecimal calculateBasicCostForStandardTerm(int days) {
        if (days <= MAX_DAYS_FOR_SHORT_TERM_RENTAL) {
            return calculateBasicCostForShortTerm(days);
        } else {
            BigDecimal standardTermDays = BigDecimal.valueOf(days - MAX_DAYS_FOR_SHORT_TERM_RENTAL);
            BigDecimal costOfShortTermRental = calculateBasicCostForShortTerm(MAX_DAYS_FOR_SHORT_TERM_RENTAL);
            BigDecimal costOfStandardTermRental = BASIC_COST_FOR_ONE_DAY.multiply(standardTermDays)
                    .multiply(IMPACT_ON_THE_PRICE_OF_STANDARD_TERM_RENTAL);
            return costOfShortTermRental.add(costOfStandardTermRental);
        }
    }

     BigDecimal calculateBasicCost(int days) {
        if (days <= MAX_DAYS_FOR_STANDARD_TERM_RENTAL) {
            return calculateBasicCostForStandardTerm(days);
        } else {
            BigDecimal longTermDays = BigDecimal.valueOf(days - MAX_DAYS_FOR_STANDARD_TERM_RENTAL);
            BigDecimal costOfStandardTermRental = calculateBasicCostForStandardTerm(MAX_DAYS_FOR_STANDARD_TERM_RENTAL);
            BigDecimal costOfLongTermRental = BASIC_COST_FOR_ONE_DAY.multiply(longTermDays).multiply(IMPACT_ON_THE_PRICE_OF_LONG_TERM_RENTAL);
            return costOfStandardTermRental.add(costOfLongTermRental);
        }
    }



}
