/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.amri.tokoshop.tokoshop.dao.rowmapper;

import co.amri.tokoshop.tokoshop.entity.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author LENOVO
 */
public class ProductRowMapper implements RowMapper<Product>{

    @Override
    public Product mapRow(ResultSet rs, int i) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getInt("price"));
//        product.setCategoryId(rs.getInt("id_category"));
        return product;
    }
    
}
