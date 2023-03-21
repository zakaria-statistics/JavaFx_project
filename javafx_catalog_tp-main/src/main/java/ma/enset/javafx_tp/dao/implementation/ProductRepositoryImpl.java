package ma.enset.javafx_tp.dao.implementation;

import ma.enset.javafx_tp.dao.ProductRepository;
import ma.enset.javafx_tp.dao.connectiondb.SingletonConnectionDB;
import ma.enset.javafx_tp.entities.Category;
import ma.enset.javafx_tp.entities.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public Product findById(Long id) {
        Connection connection = SingletonConnectionDB.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String sqlQuery = "SELECT * FROM PRODUCT WHERE productId = ?";
        Product product = null;
        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setLong(1,id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                product = new Product(
                        resultSet.getLong("productId"),
                        resultSet.getString("name"),
                        resultSet.getString("ref"),
                        resultSet.getDouble("price")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(product == null){
            throw new RuntimeException("product not found!!");
        }

        return product;
    }

    @Override
    public List<Product> findAll() {
        Connection connection = SingletonConnectionDB.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String sqlQuery = "SELECT P.*, C.name as categoryName FROM PRODUCT P INNER JOIN CATEGORY C on P.categoryId=C.categoryId";
        List<Product> products = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                products.add(new Product(
                        resultSet.getLong("productId"),
                        resultSet.getString("name"),
                        resultSet.getString("ref"),
                        resultSet.getDouble("price"),
                        new Category(
                                resultSet.getLong("categoryId"),
                                resultSet.getString("categoryName")
                        )
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    @Override
    public Product save(Product product) {
        Connection connection = SingletonConnectionDB.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String sqlQuery = "INSERT INTO PRODUCT(name,ref,price,categoryId) VALUES (?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1,product.getName());
            preparedStatement.setString(2,product.getReference());
            preparedStatement.setDouble(3,product.getPrice());
            preparedStatement.setLong(4,product.getCategory().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return product;
    }

    @Override
    public Product update(Product product) {
        Connection connection = SingletonConnectionDB.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String sqlQuery = "UPDATE PRODUCT set name = ?, ref = ?, price = ?, categoryId = ? WHERE productId = ?";
        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1,product.getName());
            preparedStatement.setString(2,product.getReference());
            preparedStatement.setDouble(3,product.getPrice());
            preparedStatement.setLong(4,product.getCategory().getId());
            preparedStatement.setLong(5,product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return product;
    }

    @Override
    public void delete(Long id) {
        Connection connection = SingletonConnectionDB.getConnection();
        PreparedStatement preparedStatement;
        String sqlQuery = "DELETE FROM PRODUCT WHERE productId = ?";
        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> findByQuery(String query) {
        Connection connection = SingletonConnectionDB.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String sqlQuery = "SELECT P.*, C.name as categoryName FROM PRODUCT P INNER JOIN CATEGORY C on P.categoryId=C.categoryId WHERE P.name like ? or P.ref like ? or P.price like ? or C.name like ?";
        List<Product> products = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.setString(1,"%"+query+"%");
            preparedStatement.setString(2,"%"+query+"%");
            preparedStatement.setString(3,"%"+query+"%");
            preparedStatement.setString(4,"%"+query+"%");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                products.add(new Product(
                        resultSet.getLong("productId"),
                        resultSet.getString("name"),
                        resultSet.getString("ref"),
                        resultSet.getDouble("price"),
                        new Category(
                                resultSet.getLong("categoryId"),
                                resultSet.getString("categoryName")
                        )
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }
}
