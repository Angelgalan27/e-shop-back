package es.eshop.app.controller;

import es.eshop.app.model.OpinionProductDTO;
import es.eshop.app.service.IOpinionProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/opinionproduct")
public class OpinionProductController {

    private final IOpinionProductService opinionProductService;

    @GetMapping("/getbyuserandproduct")
    public ResponseEntity<List<OpinionProductDTO>> getAllByUserAndProduct(@RequestParam("userId") Long userId, @RequestParam("productId") Long productId){
        return new ResponseEntity<>(opinionProductService.getAllByUserAndProductId(userId, productId), HttpStatus.OK);
    }

    @GetMapping("/getbyuser")
    public ResponseEntity<List<OpinionProductDTO>> getAllByUser(@RequestParam("userId") Long userId){
        return new ResponseEntity<>(opinionProductService.getAllByUser(userId), HttpStatus.OK);
    }

    @GetMapping("/getbyproduct")
    public ResponseEntity<List<OpinionProductDTO>> getAllByProduct(@RequestParam("productId") Long productId){
        return new ResponseEntity<>(opinionProductService.getAllByProductId(productId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OpinionProductDTO> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(opinionProductService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OpinionProductDTO> save(@RequestBody OpinionProductDTO opinionProductDTO) {
        return  new ResponseEntity<>(opinionProductService.save(opinionProductDTO), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<OpinionProductDTO> update(@RequestBody OpinionProductDTO opinionProductDTO) {
        return new ResponseEntity<>(opinionProductService.update(opinionProductDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(opinionProductService.deleteById(id), HttpStatus.OK);
    }
}
