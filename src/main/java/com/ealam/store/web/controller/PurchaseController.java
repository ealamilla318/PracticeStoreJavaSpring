package com.ealam.store.web.controller;

import com.ealam.store.domain.Purchase;
import com.ealam.store.domain.service.PurchaseService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/all")
    public ResponseEntity<List<Purchase>> getAll(){
        return new ResponseEntity<>(purchaseService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<List<Purchase>> getByClient(@PathVariable("id") String clientId){
        Optional<List<Purchase>> purchases = purchaseService.getByClient(clientId);
        return purchases.map(purchases1 -> new ResponseEntity(purchases1,HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/")
    public ResponseEntity<Purchase> save(@RequestBody Purchase purchase){
        return new ResponseEntity<>(purchaseService.save(purchase),HttpStatus.CREATED);
    }
}
