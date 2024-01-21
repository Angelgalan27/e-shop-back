package es.eshop.app.controller;

import es.eshop.app.model.FilterProductDTO;
import es.eshop.app.model.ProductDTO;
import es.eshop.app.model.ResponseProductPaginationDTO;
import es.eshop.app.service.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private final IProductService productService;

    @GetMapping
    public ResponseEntity<ResponseProductPaginationDTO> getAllByFilter(@RequestBody(required = false) FilterProductDTO filter){
        return new ResponseEntity<>(productService.getAllByFilter(filter), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(productService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> save(@RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.save(productDTO), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ProductDTO> update(@RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.update(productDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id ) {
        return new ResponseEntity<>(productService.deleteById(id), HttpStatus.OK);
    }

    @PostMapping("/uploadfile")
    public ResponseEntity<Boolean> uploadFile(@RequestParam("files") List<MultipartFile> files, @RequestParam("productId") Long productId) {
        return new ResponseEntity<>(productService.uploadFile(files, productId), HttpStatus.OK);
    }

    @DeleteMapping("/deletefile")
    public ResponseEntity<Boolean> deleteFile(@RequestParam("productId") Long productId, @RequestParam("fileId") Long fileId) {
        return new ResponseEntity<>(productService.deleteFile(productId, fileId), HttpStatus.OK);
    }
}
