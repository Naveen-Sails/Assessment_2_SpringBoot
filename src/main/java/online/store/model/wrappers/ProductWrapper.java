package online.store.model.wrappers;



import online.store.model.Product;
import online.store.model.dto.ProductDTO;

import java.util.Collections;
import java.util.List;

public class ProductWrapper<T> {
    private List<T> products = Collections.EMPTY_LIST;

    public ProductWrapper(List<T> products){
        this.products = Collections.unmodifiableList(products);
    }

    public List<T> getProducts() {
        return products;
    }

}
