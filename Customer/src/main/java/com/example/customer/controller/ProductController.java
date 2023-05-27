package com.example.customer.controller;

import com.example.library.dto.CategoryDto;
import com.example.library.model.Category;
import com.example.library.model.Product;
import com.example.library.service.CategoryService;
import com.example.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/shop/sanpham")
    public String products(Model model){
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
        List<Product> products = productService.getAllProducts();
        List<Product> listViewProducts = productService.listViewProducts();
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categoryDtoList);
        model.addAttribute("categories1", categories);
        model.addAttribute("viewProducts", listViewProducts);
        model.addAttribute("products", products);
        return "SanPham";
    }

    @GetMapping("/shop/find-product/{id}")
    public String findProductById(@PathVariable("id") Long id, Model model){
        Product product = productService.getProductById(id);
        Long categoryId = product.getCategory().getId();
        List<Product>  products = productService.getRelatedProducts(categoryId);
        model.addAttribute("product", product);
        model.addAttribute("products", products);
        return "product-detail";
    }


}
