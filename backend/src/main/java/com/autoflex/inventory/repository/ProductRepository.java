package com.autoflex.inventory.repository;

import com.autoflex.inventory.entity.Product;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {

    public List<Product> findByValueRange(BigDecimal minValue, BigDecimal maxValue) {
        return find("value between ?1 and ?2", minValue, maxValue).list();
    }

    public List<Product> findAllOrderByValueDesc() {
        return find("order by value desc").list();
    }

    public List<Product> findByNameContaining(String name) {
        return find("LOWER(name) like LOWER(?1)", "%" + name + "%").list();
    }

    public boolean existsByCodeAndIdNot(String code, Long id) {
        return count("code = ?1 and id != ?2", code, id) > 0;
    }
}