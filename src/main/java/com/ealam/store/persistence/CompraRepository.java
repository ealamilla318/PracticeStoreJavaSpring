package com.ealam.store.persistence;

import com.ealam.store.domain.Purchase;
import com.ealam.store.domain.repository.PurchaseRepository;
import com.ealam.store.persistence.crud.CompraCrudRepository;
import com.ealam.store.persistence.entity.Compra;
import com.ealam.store.persistence.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CompraRepository implements PurchaseRepository {
    @Autowired
    private CompraCrudRepository compraCrudRepository;
    @Autowired
    private PurchaseMapper mapper;
    @Override
    public List<Purchase> getAll() {
        return mapper.toPurchases((List<Compra>) compraCrudRepository.findAll());
    }

    @Override
    public Optional<List<Purchase>> getByClient(String clientId) {
        Optional<List<Compra>> compras = compraCrudRepository.findByIdCliente(clientId);
        return compras.map( compras1 -> mapper.toPurchases(compras1));
    }

    @Override
    public Purchase save(Purchase purchase) {
        Compra compra = mapper.toCompra(purchase);
        System.out.println(compra.getProductos());
        compra.getProductos().forEach(producto1 -> producto1.setCompra(compra));

        return mapper.toPurchase(compraCrudRepository.save(compra));
    }
}
