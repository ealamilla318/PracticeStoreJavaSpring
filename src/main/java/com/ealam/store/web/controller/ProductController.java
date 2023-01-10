package com.ealam.store.web.controller;

import java.util.List;
import java.util.Optional;

import com.ealam.store.domain.Product;
import com.ealam.store.domain.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.HTML;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    @ApiOperation("Obtiene todo los productos del supermercado")
    @ApiResponse(code=200,message = "OK")
    public ResponseEntity<List<Product>> getAll(){
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    };

    @GetMapping("/{id}")
    @ApiOperation("Obtiene un producto en base al id del mismo")
    @ApiResponses({
            @ApiResponse(code=200,message = "OK"),
            @ApiResponse(code=404,message = "Product not found"),
    })
    public ResponseEntity<Product> getProduct(@ApiParam(value = "El identificador del producto",required = true,example = "7") @PathVariable("id") int productId){
        Optional<Product> product = productService.getProduct(productId);
        return product.map(product1 -> new ResponseEntity<>(product1,HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable("id") int categoryId){
        Optional<List<Product>> products = productService.getByCategory(categoryId);
        return products.map(products1 -> new ResponseEntity<>(products1,HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    public ResponseEntity<Product> save(@RequestBody Product product){
        return new ResponseEntity<>(productService.save(product),HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") int productId){
        if(productService.delete(productId)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
