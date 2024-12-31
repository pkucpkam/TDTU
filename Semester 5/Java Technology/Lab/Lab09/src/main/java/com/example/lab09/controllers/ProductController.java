package com.example.lab09.controllers;


import com.example.lab09.model.product.Product;
import com.example.lab09.model.product.ProductService;
import com.example.lab09.payload.ProductResponse;
import com.example.lab09.utils.ImageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService services;

    @GetMapping("")
    public List<Product> index(){
        return services.getAll();
    }

    @PostMapping(value = "")
    public ResponseEntity<ProductResponse> add(@RequestParam("illustration") MultipartFile file,
                                               @RequestParam("id") String id,
                                               @RequestParam("name") String name,
                                               @RequestParam("price") int price,
                                               @RequestParam("description") String description)
            throws IOException {

        services.add(Product.builder()
                .id(id)
                .description(description)
                .price(price)
                .name(name)
                .illustration(ImageUtility.compressImage(file.getBytes()))
                .build());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ProductResponse("Product added successfully", services.findById(id)));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Product addFormUrlEncoded(Product product){
        return services.add(product);
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") String id){
        return services.findById(id);
    }

    @PutMapping(value ="/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("id") String id,
                                                         @RequestParam("illustration") MultipartFile file,
                                                         @RequestParam("name") String name,
                                                         @RequestParam("price") int price,
                                                         @RequestParam("description") String description) throws IOException {

        services.update(id, Product.builder()
                .id(id)
                .description(description)
                .price(price)
                .name(name)
                .illustration(ImageUtility.compressImage(file.getBytes()))
                .build());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ProductResponse("Product Updated successfully", services.findById(id)));
    }

    @PutMapping(value ="/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Product updateProductFormUrlEncoded(@PathVariable("id") String id, Product product){
        Product updateProduct = services.findById(id);

        updateProduct.setName(product.getName());
        updateProduct.setIllustration(product.getIllustration());
        updateProduct.setPrice(product.getPrice());
        updateProduct.setDescription(product.getDescription());

        return (services.update(id, product)? updateProduct : services.findById(id));
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<ProductResponse> updatePatch(@PathVariable String id,
                                                       @RequestParam(value = "illustration", required = false) MultipartFile file,
                                                       @RequestParam(value = "name", required = false) String name,
                                                       @RequestParam(value = "price", required = false) Integer price,
                                                       @RequestParam(value = "description", required = false) String description)
            throws IOException {
        Product product = services.findById(id);
        String updatedName = (name != null) ? name : product.getName();
        String updatedDescription = (description != null) ? description : product.getDescription();
        int updatedPrice = (price != null) ? price : product.getPrice();
        byte[] updatedIllustration = (file != null) ? ImageUtility.compressImage(file.getBytes()) : product.getIllustration();

        Product updatedProduct = services.updatePatch(id, Product.builder()
                .id(id)
                .description(updatedDescription)
                .price(updatedPrice)
                .name(updatedName)
                .illustration(updatedIllustration)
                .build());
        if (updatedProduct == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ProductResponse("Product Updated successfully", services.findById(id)));
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<ProductResponse> updatePatchFormUrlEncoded(@PathVariable String id, Product product) {
        Product updatedProduct = services.updatePatch(id, product);
        if (updatedProduct == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ProductResponse("Product Updated successfully", services.findById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable("id") String id) {
        services.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
               .body(new ProductResponse("Product added successfully", services.findById(id)));
    }
}
