package com.example.customer.controller;

import com.example.library.dto.CategoryDto;
import com.example.library.dto.ProductDto;
import com.example.library.model.Category;
import com.example.library.model.Product;
import com.example.library.service.CategoryService;
import com.example.library.service.CustomerService;
import com.example.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CustomerService customerService;

//    @RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
//    public String home(Model model, Principal principal, HttpSession session){
//        if(principal != null){
//            session.setAttribute("username", principal.getName());
//            Customer customer = customerService.findByUsername(principal.getName());
//            ShoppingCart cart = customer.getShoppingCart();
//            session.setAttribute("totalItems", cart.getTotalItems());
//        }else{
//            session.removeAttribute("username");
//        }
//        return "home";
//    }

    @GetMapping("/shop")
    public String index(Model model){
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
        List<Product> products = productService.getAllProducts();
        List<Product> listViewProducts = productService.listViewProducts();
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categoryDtoList);
        model.addAttribute("categories1", categories);
        model.addAttribute("viewProducts", listViewProducts);
        model.addAttribute("products", products);
        return "Index";
    }
}