//package com.inv.invmaster001.controller;
//
//import com.inv.invmaster001.dto.entitydto.ProductDTO;
//import com.inv.invmaster001.service.ProductService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/products")
//public class ProductController {
//
//    @Autowired
//    private ProductService productService;
//
//    @PostMapping
//    public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO productDTO) {
//        ProductDTO savedProduct = productService.saveProduct(productDTO);
//        return ResponseEntity.ok(savedProduct);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
//        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
//        return ResponseEntity.ok(updatedProduct);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
//        productService.deleteProduct(id);
//        return ResponseEntity.noContent().build();
//    }
//}