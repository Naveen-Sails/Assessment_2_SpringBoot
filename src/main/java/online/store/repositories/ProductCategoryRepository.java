package online.store.repositories;

import online.store.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Michael Pogrebinsky - www.topdeveloperacademy.com
 * This represents an API through which we can perform CRUD operations against the
 * ProductsCategories table in the database
 * You do not need to modify this file
 */
@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    /**
     * Returns all the available categories of products in our online store
     */

    //Overriding default findAll
    @Query("SELECT c FROM ProductCategory c")
    List<ProductCategory> findAll();
}
