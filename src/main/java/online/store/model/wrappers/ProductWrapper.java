package online.store.model.wrappers;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductWrapper<T> {
    private final List<T> products;
    private final Long totalItems;
    private final Integer totalPages;
    private final Integer currentPage;

    public ProductWrapper(List<T> products) {
        this.products = Collections.unmodifiableList(products);
        this.totalItems = null;
        this.totalPages = null;
        this.currentPage = null;
    }

    public ProductWrapper(Page<T> page) {
        this.products = Collections.unmodifiableList(page.getContent());
        this.totalItems = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.currentPage = page.getNumber();
    }

    public List<T> getProducts() {
        return products;
    }

    public Long getTotalItems() {
        return totalItems;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }
}
