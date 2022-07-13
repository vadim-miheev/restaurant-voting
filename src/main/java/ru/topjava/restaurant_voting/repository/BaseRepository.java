package ru.topjava.restaurant_voting.repository;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.restaurant_voting.error.AppException;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;


@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM #{#entityName} u WHERE u.id=:id")
    int delete(int id);

    default void deleteExisted(int id) {
        if (delete(id) == 0) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Entity with id=" + id + " not found",
                    ErrorAttributeOptions.of(MESSAGE));
        }
    }
}