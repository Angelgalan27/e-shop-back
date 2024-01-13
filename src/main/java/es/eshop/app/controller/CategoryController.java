package es.eshop.app.controller;

import es.eshop.app.model.CategoryDTO;
import es.eshop.app.service.ICategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;


    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAll() {
       return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
   }

   @GetMapping("/{id}")
   public  ResponseEntity<CategoryDTO> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(categoryService.getById(id), HttpStatus.OK);
   }

   @PostMapping
   public ResponseEntity<CategoryDTO> save(@RequestBody CategoryDTO categoryDTO) {
        return new ResponseEntity<>(categoryService.save(categoryDTO), HttpStatus.OK);
   }

   @PutMapping
   public ResponseEntity<CategoryDTO> update(@RequestBody CategoryDTO categoryDTO) {
        return new ResponseEntity<>(categoryService.update(categoryDTO), HttpStatus.OK);
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id){
        return new ResponseEntity<>(categoryService.deleteById(id), HttpStatus.OK);
   }
}
