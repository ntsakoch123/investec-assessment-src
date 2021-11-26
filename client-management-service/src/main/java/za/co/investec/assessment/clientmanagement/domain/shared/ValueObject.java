package za.co.investec.assessment.clientmanagement.domain.shared;


public interface ValueObject<T> {

     boolean sameValueAs(T other);

}
