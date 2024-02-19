package org.example.productservice.controllers.advices;

import org.example.productservice.dtos.ExceptionDto;
import org.example.productservice.exceptions.ProductAlreadyExistsException;
import org.example.productservice.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
// this advice if you want to specify the advice specific to the controller
//@ControllerAdvice(assignableTypes = {ProductController.class})
public class ProductServiceAdvice {


    @ExceptionHandler(ProductNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ResponseEntity<ExceptionDto> handleProductNotFoundException(ProductNotFoundException e) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(e.getMessage());
        exceptionDto.setStatus("Failure");
//        ResponseEntity<ExceptionDto> responseEntity = new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    private ResponseEntity<ExceptionDto> handleProductAlreadyExistsException(ProductAlreadyExistsException e) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(e.getMessage());
        exceptionDto.setStatus("Failure");
        return new ResponseEntity<>(exceptionDto, HttpStatus.CONFLICT);
    }


}
