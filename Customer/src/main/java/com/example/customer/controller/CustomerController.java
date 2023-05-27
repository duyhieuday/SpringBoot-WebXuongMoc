package com.example.customer.controller;

import com.example.library.dto.CategoryDto;
import com.example.library.dto.CustomerDto;
import com.example.library.dto.ProductDto;
import com.example.library.model.Category;
import com.example.library.model.Product;
import com.example.library.model.TinTuc;
import com.example.library.model.User;
import com.example.library.repository.UserRepository;
import com.example.library.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/shop")
public class CustomerController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PostService postService;

    @Autowired
    CustomerService customerService;

//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public String login() {
//        return "login";
//    }

    @RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
    public String home(Model model, Principal principal, HttpSession session){
        if(principal != null){
            session.setAttribute("username", principal.getName());
            User user = customerService.findByUsername(principal.getName());
        }else{
            session.removeAttribute("username");
        }
        List<Category> categories = categoryService.findAll();
        List<ProductDto> productDtos = productService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("products", productDtos);
        return "Index";
    }


    @RequestMapping(value = {"/chitiet"}, method = RequestMethod.GET)
    public String chitietPage(Model model, Principal principal, HttpSession session){
        if(principal != null){
            session.setAttribute("username", principal.getName());
            User user = customerService.findByUsername(principal.getName());
        }else{
            session.removeAttribute("username");
        }
        return "chitiet";
    }

//    @RequestMapping(value = {"/tatcasp"}, method = RequestMethod.GET)
//    public String tatcaspPage(Model model, Principal principal, HttpSession session){
//        if(principal != null){
//            session.setAttribute("username", principal.getName());
//            User user = customerService.findByUsername(principal.getName());
//        }else{
//            session.removeAttribute("username");
//        }
//        return "tatcasp";
//    }


    @RequestMapping(value = {"/doitac"}, method = RequestMethod.GET)
    public String doitacPage(Model model, Principal principal, HttpSession session){
        if(principal != null){
            session.setAttribute("username", principal.getName());
            User user = customerService.findByUsername(principal.getName());
        }else{
            session.removeAttribute("username");
        }
        return "DoiTac";
    }

    @RequestMapping(value = {"/gioithieu"}, method = RequestMethod.GET)
    public String gioithieuPage(Model model, Principal principal, HttpSession session){
        if(principal != null){
            session.setAttribute("username", principal.getName());
            User user = customerService.findByUsername(principal.getName());
        }else{
            session.removeAttribute("username");
        }
        return "GioiThieu";
    }

//    @GetMapping("/index")
//    public String indexPage() {
//
//        return "Index";
//    }

    @RequestMapping(value = {"/lienhe"}, method = RequestMethod.GET)
    public String lienhePage(Model model, Principal principal, HttpSession session){
        if(principal != null){
            session.setAttribute("username", principal.getName());
            User user = customerService.findByUsername(principal.getName());
        }else{
            session.removeAttribute("username");
        }
        return "LienHe";
    }

    @RequestMapping(value = {"/tatcasp"}, method = RequestMethod.GET)
    public String sanphamPage(Model model, Principal principal, HttpSession session){
        if(principal != null){
            session.setAttribute("username", principal.getName());
            User user = customerService.findByUsername(principal.getName());
        }else{
            session.removeAttribute("username");
        }
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
        List<Product> products = productService.getAllProducts();
        List<Product> listViewProducts = productService.listViewProducts();
        model.addAttribute("categories", categoryDtoList);
        model.addAttribute("viewProducts", listViewProducts);
        model.addAttribute("products", products);
        return "tatcasp";
    }

    @RequestMapping(value = {"/tintuc"}, method = RequestMethod.GET)
    public String tintucPage(Model model, Principal principal, HttpSession session){
        if(principal != null){
            session.setAttribute("username", principal.getName());
            User user = customerService.findByUsername(principal.getName());
        }else{
            session.removeAttribute("username");
        }
        List<TinTuc> tinTucs = postService.findAll();
        model.addAttribute("tintucs", tinTucs);
        return "Tin_Tuc";
    }

    @RequestMapping(value = {"/trangchu"}, method = RequestMethod.GET)
    public String trangchuPage(Model model, Principal principal, HttpSession session){
        if(principal != null){
            session.setAttribute("username", principal.getName());
            User user = customerService.findByUsername(principal.getName());
        }else{
            session.removeAttribute("username");
        }
        return "Trang_Chu";
    }

}
