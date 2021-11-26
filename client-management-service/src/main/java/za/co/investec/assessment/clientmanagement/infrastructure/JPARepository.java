package za.co.investec.assessment.clientmanagement.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface JPARepository<T,I> extends JpaRepository<T,I> {

    default List<T> findAll() {
        throw new UnsupportedOperationException();
    }

    default List<T> findAllById(Iterable<I> ids) {
        throw new UnsupportedOperationException();
    }

    default void deleteAll() {
        throw new UnsupportedOperationException();
    }

    default long count() {
        throw new UnsupportedOperationException();
    }

}
