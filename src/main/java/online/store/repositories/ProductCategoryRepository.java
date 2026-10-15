package online.store.repositories;

import online.store.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;


public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Override
    @Query("SELECT category FROM ProductCategory category")
    @NonNull
    List<ProductCategory> findAll();
}
