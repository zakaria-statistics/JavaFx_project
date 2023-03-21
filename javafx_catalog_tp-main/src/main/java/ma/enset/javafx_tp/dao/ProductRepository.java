package ma.enset.javafx_tp.dao;

import ma.enset.javafx_tp.entities.Product;

import java.util.List;

public interface ProductRepository extends DAO<Product, Long>{

    List<Product> findByQuery(String query);

}
