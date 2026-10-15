package online.store.repositories;

import online.store.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    @Query("SELECT p FROM Product p")
    @NonNull
    List<Product> findAll();

    @Override
    @Query("SELECT p FROM Product p WHERE p.id = :productId")
    @NonNull
    Optional<Product> findById(@NonNull @Param("productId") Long productId);

    @Override
    @Query("SELECT p FROM Product p ORDER BY p.id")
    @NonNull
    Page<Product> findAll(@NonNull Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category = :productCategory")
    List<Product> findByCategory(@Param("productCategory") String productCategory);

    /**
     * Applies only the filters that have values and returns the requested page.
     * Search checks product names and descriptions without case sensitivity.
     */
    @Query(
            value = "SELECT p FROM Product p " +
                    "WHERE (:category IS NULL OR :category = '' OR p.category = :category) " +
                    "AND (:minPrice IS NULL OR p.priceUSD >= :minPrice) " +
                    "AND (:maxPrice IS NULL OR p.priceUSD <= :maxPrice) " +
                    "AND (:search IS NULL OR :search = '' " +
                    "OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
                    "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%'))) " +
                    "ORDER BY p.id"
    )
    Page<Product> findProducts(
            @Param("category") String category,
            @Param("minPrice") Float minPrice,
            @Param("maxPrice") Float maxPrice,
            @Param("search") String search,
            Pageable pageable
    );

    /**
     * Returns at most the requested number of products from the first page.
     */
    default List<Product> findAtMostNumberOfProducts(int numberOfProducts) {
        Page<Product> page = findAll(PageRequest.of(0, numberOfProducts));
        return page.toList();
    }
}
