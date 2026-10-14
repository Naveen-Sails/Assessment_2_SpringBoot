package online.store.repositories;

import online.store.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author Michael Pogrebinsky - www.topdeveloperacademy.com
 * This represents an API through which we can perform CRUD operations against the
 * ProductsCategories table in the database
 * You do not need to modify this file
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Override
    @Query("SELECT category FROM ProductCategory category")
    @NonNull
    List<ProductCategory> findAll();
}
