package za.co.investec.assessment.clientmanagement.domain.shared;

public interface EntityObject<T> {
    boolean sameIdentityAs(T other);
}
