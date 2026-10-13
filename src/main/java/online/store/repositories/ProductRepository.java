package online.store.repositories;

import online.store.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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
    List<Product> findAll();

    /**
     * Looks for a product by id in the Products table
     * @return the product that matches the given id or Optional.empty
     */
    Optional<Product> findById(Long productId);

    /**
     * Looks for all the products that match the given category
     */
    List<Product> findByCategory(String productCategory);

    /**
     * Returns at most numberOfProducts from a database
     */
    default List<Product> findAtMostNumberOfProducts(int numberOfProducts) {
        Page<Product> page = findAll(PageRequest.of(0, numberOfProducts));
        return page.toList();
    }

    @Query("SELECT p FROM Product p JOIN p.category c WHERE " +
            "(:category IS NULL OR c.category = :category) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND" +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND" +
            "(:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<Product> findWithDynamicFilters(
            @Param("category") String category,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("search") String search
            );
}
