package online.store.repositories;

import online.store.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Michael Pogrebinsky - www.topdeveloperacademy.com
 * This represents an API through which we can perform CRUD operations against the
 * Products table in the database
 * You do not need to modify this file
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Returns all products in the Products table
     */

    //Overriding the default findAll
    @Query("SELECT p FROM Product p")
    List<Product> findAll();

    /**
     * Looks for a product by id in the Products table
     * @return the product that matches the given id or Optional.empty
     */

    //Overriding the default findById
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Optional<Product> findById(@Param("id") Long productId);

    /**
     * Looks for all the products that match the given category
     */

    //Overriding the default findByCategory
    @Query("SELECT p FROM Product p WHERE p.category = :productCategory")
    List<Product> findByCategory(@Param("productCategory") String productCategory);

    /**
     * Returns at most numberOfProducts from a database
     */
    //JPQL does not supports LIMIT keyword
    default List<Product> findAtMostNumberOfProducts(int numberOfProducts) {
        Page<Product> page = findAll(PageRequest.of(0, numberOfProducts));
        return page.toList();
    }
}
