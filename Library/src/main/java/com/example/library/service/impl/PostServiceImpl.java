package com.example.library.service.impl;

import com.example.library.dto.ProductDto;
import com.example.library.model.Product;
import com.example.library.model.TinTuc;
import com.example.library.repository.ProductRepository;
import com.example.library.repository.TinTucRepository;
import com.example.library.service.PostService;
import com.example.library.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private TinTucRepository tinTucRepository;

    @Autowired
    private ImageUpload imageUpload;

    /*Admin*/
    @Override
    public List<TinTuc> findAll() {
//        List<TinTuc> tinTucs = tinTucRepository.findAll();
//        List<TinTuc> tinTucList = transfer(tinTucs);
        return tinTucRepository.findAll();
    }

    public void delete(Long id) throws UserNotFoundException {
        Long count = tinTucRepository.countById(id);
        if(count == null || count == 0){
            throw new UserNotFoundException("Could not find post with ID" + id);
        }
        tinTucRepository.deleteById(id);
    }

    @Override
    public TinTuc save(MultipartFile imageProduct, TinTuc tinTuc1) {
        try {
            TinTuc tinTuc = new TinTuc();
            if(imageProduct == null){
                tinTuc.setImage(null);
            }else{
                if(imageUpload.uploadImage(imageProduct)){
                    System.out.println("Upload successfully");
                }
                tinTuc.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            tinTuc.setTitle(tinTuc1.getTitle());
            tinTuc.setDescription(tinTuc1.getDescription());
            return tinTucRepository.save(tinTuc);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public TinTuc update(MultipartFile imageProduct ,TinTuc tinTuc1) {
        try {
            TinTuc tinTuc = tinTucRepository.getById(tinTuc1.getId());
            if(imageProduct == null){
                tinTuc.setImage(tinTuc.getImage());
            }else{
                if(imageUpload.checkExisted(imageProduct) == false){
                    imageUpload.uploadImage(imageProduct);
                }
                tinTuc.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            tinTuc.setTitle(tinTuc1.getTitle());
            tinTuc.setDescription(tinTuc1.getDescription());
            return tinTucRepository.save(tinTuc);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }



    @Override
    public TinTuc getById(Long id) {
        TinTuc tinTuc = tinTucRepository.getById(id);
        TinTuc tinTuc1 = new TinTuc();
        tinTuc1.setId(tinTuc.getId());
        tinTuc1.setTitle(tinTuc.getTitle());
        tinTuc1.setDescription(tinTuc.getDescription());
        tinTuc1.setImage(tinTuc.getImage());
        return tinTuc1;
    }





    private List<TinTuc> transfer(List<TinTuc> tinTucs){
        List<TinTuc> tinTucList = new ArrayList<>();
        for(TinTuc tinTuc : tinTucs){
            TinTuc tinTuc1 = new TinTuc();
            tinTuc1.setId(tinTuc.getId());
            tinTuc1.setTitle(tinTuc.getTitle());
            tinTuc1.setDescription(tinTuc.getDescription());
            tinTuc1.setImage(tinTuc.getImage());
        }
        return tinTucList;
    }


    /*Customer*/


    @Override
    public TinTuc getProductById(Long id) {
        return tinTucRepository.getById(id);
    }

    }

