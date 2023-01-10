package com.ealam.store.persistence;

import com.ealam.store.domain.Product;
import com.ealam.store.domain.repository.ProductRepository;
import com.ealam.store.persistence.crud.ProductoCrudRepository;
import com.ealam.store.persistence.entity.Producto;
import com.ealam.store.persistence.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductoRepository implements ProductRepository {

    @Autowired
    private ProductoCrudRepository productoCrudRepository;
    @Autowired
    private ProductMapper mapper;

    @Override
    public List<Product> getAll() {
        List<Producto> productos = (List<Producto>) productoCrudRepository.findAll();
        return mapper.toProducts(productos);
    }

    @Override
    public Optional<List<Product>> getByCategory(int categoryId) {
        List<Producto> productos = productoCrudRepository.findByIdCategoriaOrderByNombreAsc(categoryId);
        return Optional.of(mapper.toProducts(productos));
    }

    @Override
    public Optional<List<Product>> getScarseProducts(int quantity) {
        Optional<List<Producto>> productos = productoCrudRepository.findByCantidadStockLessThanAndEstado(quantity,true);
        return productos.map(productos1 -> mapper.toProducts(productos1));
    }

    @Override
    public Optional<Product> getProduct(int productId) {
        Optional<Producto> producto = productoCrudRepository.findById(productId);
        return producto.map(producto1 -> mapper.toProduct(producto1));
    }

    @Override
    public Product save(Product product) {
        return mapper.toProduct(productoCrudRepository.save(mapper.toProducto(product)));
    }

    @Override
    public void delete(int productId) {
        productoCrudRepository.deleteById(productId);
    }

}