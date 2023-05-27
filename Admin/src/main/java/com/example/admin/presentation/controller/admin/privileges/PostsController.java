package com.example.admin.presentation.controller.admin.privileges;


import com.example.admin.presentation.controller.BaseController;
import com.example.admin.presentation.internalmodel.Fragment;
import com.example.admin.presentation.internalmodel.Resource;
import com.example.library.dto.ProductDto;
import com.example.library.model.Category;
import com.example.library.model.TinTuc;
import com.example.library.repository.TinTucRepository;
import com.example.library.repository.UserRepository;
import com.example.library.service.*;
import com.example.library.service.impl.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.management.ThreadInfo;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin/privileges/posts")
public class PostsController extends BaseController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PostService postService;

    @Autowired
    private TinTucRepository repository;

    @Override
    @RequestMapping
    public ModelAndView index() {
        List<TinTuc> tinTucList = postService.findAll();
        return view(buildContext(Resource.Layout.MasterAdmin)
                .setTitle("Posts")
                .setContent(Fragment.of("templates/admin/pages/privileges/posts.html")))
                .addObject("posts", tinTucList);
    }

    @GetMapping("/add-post")
    public ModelAndView addProductForm(Model model, Principal principal){
        if(principal == null){
            return index();
        }
        return view(buildContext(Resource.Layout.MasterAdmin)
                .setTitle("Products/add-post")
                .setContent(Fragment.of("templates/admin/pages/privileges/add-post.html")))
                .addObject("tintuc", new TinTuc());


    }

    @PostMapping("/save-post")
    public ModelAndView saveProduct(@ModelAttribute("tintuc")TinTuc tinTuc,
                                    @RequestParam("imageProduct") MultipartFile imageProduct,
                                    RedirectAttributes attributes){
        try {
            postService.save(imageProduct, tinTuc);
            attributes.addFlashAttribute("success", "Add successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to add!");
        }
//        return view(buildContext(Resource.Layout.MasterAdmin)
//                .setTitle("Products")
//                .setContent(Fragment.of("templates/admin/pages/privileges/products.html")))
//                .addObject("products", repository.findAll());
        return index();
    }

    @GetMapping("/update-post/{id}")
    public ModelAndView updateProductForm(@PathVariable("id") Long id, Model model, Principal principal){
        if(principal == null){
            return index();
        }
        model.addAttribute("title", "Update posts");
        TinTuc tinTuc = postService.getById(id);
        return view(buildContext(Resource.Layout.MasterAdmin)
                .setTitle("Products/update-product")
                .setContent(Fragment.of("templates/admin/pages/privileges/update-post.html")))
                .addObject("tintuc", tinTuc)
                .addObject("title", "Update posts");

    }


    @PostMapping("/update-post/{id}")
    public ModelAndView processUpdate(@PathVariable("id") Long id,
                                      @ModelAttribute("productDto") TinTuc tinTuc,
                                      @RequestParam("imageProduct")MultipartFile imageProduct,
                                      RedirectAttributes attributes
    ){
        try {
            postService.update(imageProduct, tinTuc);
            attributes.addFlashAttribute("success", "Update successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to update!");
        }
        return index();

    }
    @GetMapping("/delete/{id}")
    public ModelAndView deleteUser(@PathVariable("id") Long id, RedirectAttributes ra) {
        try {
            postService.delete(id);
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return index();
    }

}
