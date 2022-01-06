/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.amri.tokoshop.tokoshop.dao;

import co.amri.tokoshop.tokoshop.dao.rowmapper.ProductRowMapper;
import co.amri.tokoshop.tokoshop.entity.Product;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author LENOVO
 */@Component
public class ProductDao {
    @Autowired
    private JdbcTemplate jdbctemplate;
    private ProductRowMapper productRowMapper = new ProductRowMapper();
    
  public List<Product> getProducts(){
      return jdbctemplate.query("select * from t_product", productRowMapper);
  }
  
  public Product getProductById(Integer id){
  return jdbctemplate.queryForObject("select * from t_product where id = ?", productRowMapper, id);
  }
  
  public void save (Product product){
      if (product.getId() == null) {
          jdbctemplate.update(
                  "insert into t_product(name, price, id_category) values ( ?, ?, ?)", 
                  product.getName(), product.getPrice(), product.getCategoryId());
                  
          
      }else{
          jdbctemplate.update(
                  "update t_product set name = ?, price = ? , id_category = ? where id = ?",
                  product.getName(), product.getPrice(), product.getCategoryId(), product.getId());
      }
  }
  
  public void delete(Integer id){
  jdbctemplate.update("delete from t_product where id = ?", id);
  }
  
  public void delete(){
  jdbctemplate.update("delete from t_product");
  }
}
