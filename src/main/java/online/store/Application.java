package online.store;

import online.store.model.Product;
import online.store.model.ProductCategory;
import online.store.repositories.ProductCategoryRepository;
import online.store.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "true");
        System.setProperty("spring.devtools.restart.additional-paths", "src/*");
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            ProductCategoryRepository categoryRepository,
            ProductRepository productRepository
    ) {
        return args -> {
            // Load sample catalog data when the application starts.
            populateInMemoryDatabaseWithData(categoryRepository, productRepository);
        };
    }

    /**
     * Populates the H2 in-memory database with data for testing and development purposes
     */
    private void populateInMemoryDatabaseWithData(
            ProductCategoryRepository categoryRepository,
            ProductRepository productRepository
    ) {
        addProductCategories(categoryRepository);
        addProducts(productRepository);
    }

    /**
     * Populates products in h2 in-memory DB for testing purposes
     */
    private void addProducts(ProductRepository repository) {
        List<Product> toys = Arrays.asList(
                new Product(null, "Toy Car",
                        "Great toy for boys 1-10 years old. Fun and educational",
                        "toy-car.jpeg",
                        1f,
                        "toys"),
                new Product(null, "Teddy Bear",
                        "Great toy for kids 2-12 years old. Perfect falling asleep with",
                        "teddy-bear.jpg",
                        15f,
                        "toys"));

        List<Product> electronics = Arrays.asList(
                new Product(null, "Apple Laptop",
                        "Perfect computer for programming in checking emails",
                        "laptop_640x426.jpeg",
                        700f,
                        "electronics"
                ),
                new Product(null, "Desktop Monitor",
                        "Computer monitor for gaming and watching movies",
                        "computer-monitor_640x426.jpeg",
                        299f,
                        "electronics"
                ),
                new Product(null, "Audio Speakers",
                        "Stereo speakers for listening to music at home",
                        "speakers.jpeg",
                        89f,
                        "electronics"
                ));

        Product art = new Product(null, "Color Markers",
                "Four high quality markers for any art project",
                "markers_640x426.jpeg",
                14.99f,
                "art"
        );
        Product music = new Product(null, "CD Collection",
                "Best of the 80s music collection",
                "music_640x426.jpeg",
                49.99f,
                "music"
        );

        List<Product> apparel = Arrays.asList(
                new Product(null, "Gray T-Shirt",
                        "Unisex gray shirt, small size, cotton",
                        "gray-shirt_640x426.jpeg",
                        9.99f,
                        "apparel"
                ),
                new Product(null, "Jeans",
                        "Fashionable and comfortable jeans",
                        "pants.jpg",
                        40f,
                        "apparel"
                ));
        Product jewelry = new Product(null, "Diamond Necklace",
                "Beautiful green necklace with  diamonds",
                "necklace_640x426.jpeg",
                800f,
                "jewelry"
        );

        repository.saveAll(toys);
        repository.saveAll(electronics);
        repository.save(art);
        repository.save(music);
        repository.saveAll(apparel);
        repository.save(jewelry);
    }

    /**
     * Populates all available categories in H2 database
     */
    private void addProductCategories(ProductCategoryRepository repository) {
        List<String> categories =
                Arrays.asList("toys", "electronics", "art", "music", "apparel", "jewelry");

        repository.saveAll(categories.stream()
                .map(category -> new ProductCategory(null, category))
                .collect(Collectors.toList()));
    }
}
