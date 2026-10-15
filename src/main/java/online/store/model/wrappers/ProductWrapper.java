package online.store.model.wrappers;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductWrapper<T> {
    private final List<T> products;
    private final Long totalItems;
    private final Integer totalPages;
    private final Integer currentPage;

    // Leave page fields null for list responses; Jackson omits them from JSON.
    public static <T> ProductWrapper<T> fromList(List<T> products) {
        return new ProductWrapper<>(Collections.unmodifiableList(products), null, null, null);
    }

    public static <T> ProductWrapper<T> fromPage(Page<T> page) {
        return new ProductWrapper<>(
                Collections.unmodifiableList(page.getContent()),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber()
        );
    }
}
