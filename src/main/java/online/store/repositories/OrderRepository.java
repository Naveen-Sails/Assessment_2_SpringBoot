package online.store.repositories;

import online.store.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
/**
 * @author Michael Pogrebinsky - www.topdeveloperacademy.com
 * This represents an API through which we can perform CRUD operations against the
 * Orders table in the database
 * You do not need to modify this file
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o JOIN FETCH o.product " +
            "WHERE o.firstName = :firstName AND o.lastName = :lastName")
    List<Order> findByFirstNameAndLastName(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName
    );

    @Query("SELECT o FROM Order o JOIN FETCH o.product " +
            "WHERE o.email = :email")
    List<Order> findByEmailWithProduct(@Param("email") String email);
}
