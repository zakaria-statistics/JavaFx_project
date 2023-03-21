package ma.enset.javafx_tp.service;

import ma.enset.javafx_tp.entities.Category;
import ma.enset.javafx_tp.entities.Product;

import java.util.List;

public interface CatalogService {

    Product addProduct(Product product);
    Product updateProduct(Product product);
    List<Product> findAllProducts();
    List<Product> findProductsByQuery(String query);
    void deleteProduct(Long id);

    Category addCategory(Category category);
    Category updateCategory(Category category);
    List<Category> findAllCategories();
    void deleteCategory(Long id);

}
