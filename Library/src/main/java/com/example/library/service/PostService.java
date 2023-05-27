package com.example.library.service;

import com.example.library.dto.ProductDto;
import com.example.library.model.Product;
import com.example.library.model.TinTuc;
import com.example.library.service.impl.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    /*Admin*/
    List<TinTuc> findAll();
    TinTuc save(MultipartFile imageProduct, TinTuc tinTuc);
    TinTuc update(MultipartFile imageProduct, TinTuc tinTuc);
    TinTuc getById(Long id);
    void delete(Long id) throws UserNotFoundException;



    TinTuc getProductById(Long id);


}
