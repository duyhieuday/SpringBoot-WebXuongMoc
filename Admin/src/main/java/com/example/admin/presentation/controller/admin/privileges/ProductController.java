package com.example.admin.presentation.controller.admin.privileges;

import com.example.admin.presentation.controller.BaseController;
import com.example.admin.presentation.internalmodel.Fragment;
import com.example.admin.presentation.internalmodel.Resource;
import com.example.library.dto.CategoryDto;
import com.example.library.dto.ProductDto;
import com.example.library.model.Category;
import com.example.library.model.Product;
import com.example.library.repository.ProductRepository;
import com.example.library.service.CategoryService;
import com.example.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin/privileges/products")
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductRepository repository;

    @Override
    @RequestMapping
    public ModelAndView index() {
        List<ProductDto> productDtoList = productService.findAll();
        return view(buildContext(Resource.Layout.MasterAdmin)
                .setTitle("Products")
                .setContent(Fragment.of("templates/admin/pages/privileges/products.html")))
                .addObject("products", productDtoList)
                .addObject("title", "Manage Product");
    }

//    @GetMapping("/products")
//    public ModelAndView products(Model model, Principal principal){
//        if(principal == null){
//            return index();
//        }
//        List<ProductDto> productDtoList = productService.findAll();
//        model.addAttribute("title", "Manage Product");
//        model.addAttribute("products", productDtoList);
//        model.addAttribute("size", productDtoList.size());
//        return index();
//    }

    @GetMapping("/products/{pageNo}")
    public ModelAndView productsPage(@PathVariable("pageNo") int pageNo, Model model, Principal principal){
        if(principal == null){
            return index();
        }
        Page<ProductDto> products = productService.pageProducts(pageNo);
        model.addAttribute("title", "Manage Product");
        model.addAttribute("size", products.getSize());
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("products", products);
        return index();
    }

    @GetMapping("/search-result/{pageNo}")
    public ModelAndView searchProducts(@PathVariable("pageNo")int pageNo,
                                 @RequestParam("keyword") String keyword,
                                 Model model,
                                 Principal principal){
        if(principal == null){
            return index();
        }
        Page<ProductDto> products = productService.searchProducts(pageNo, keyword);
        model.addAttribute("title", "Search Result");
        model.addAttribute("products", products);
        model.addAttribute("size", products.getSize());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", products.getTotalPages());
        return index();
    }



    @GetMapping("/add-product")
    public ModelAndView addProductForm(Model model, Principal principal){
        if(principal == null){
            return index();
        }
        List<Category> categories = categoryService.findAllByActivated();
        return view(buildContext(Resource.Layout.MasterAdmin)
                .setTitle("Products/add-product")
                .setContent(Fragment.of("templates/admin/pages/privileges/add-product.html")))
                .addObject("categories", categories)
                .addObject("product", new ProductDto());


    }

    @PostMapping("/save-product")
    public ModelAndView saveProduct(@ModelAttribute("product")ProductDto productDto,
                              @RequestParam("imageProduct") MultipartFile imageProduct,
                              RedirectAttributes attributes){
        try {
            productService.save(imageProduct, productDto);
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

    @GetMapping("/update-product/{id}")
    public ModelAndView updateProductForm(@PathVariable("id") Long id, Model model, Principal principal){
        if(principal == null){
            return index();
        }
        model.addAttribute("title", "Update products");
        List<Category> categories = categoryService.findAllByActivated();
        ProductDto productDto = productService.getById(id);
        System.out.println(productDto);
        return view(buildContext(Resource.Layout.MasterAdmin)
                .setTitle("Products/update-product")
                .setContent(Fragment.of("templates/admin/pages/privileges/update-product.html")))
                .addObject("categories", categories)
                .addObject("productDto", productDto)
                .addObject("title", "Update products");

    }


    @PostMapping("/update-product/{id}")
    public ModelAndView processUpdate(@PathVariable("id") Long id,
                                @ModelAttribute("productDto") ProductDto productDto,
                                @RequestParam("imageProduct")MultipartFile imageProduct,
                                RedirectAttributes attributes
    ){
        try {
            productService.update(imageProduct, productDto);
            attributes.addFlashAttribute("success", "Update successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to update!");
        }
        return index();

    }

    @RequestMapping(value = "/enable-product/{id}", method = {RequestMethod.PUT , RequestMethod.GET})
    public ModelAndView enabledProduct(@PathVariable("id")Long id, RedirectAttributes attributes){
        try {
            productService.enableById(id);
            attributes.addFlashAttribute("success", "Enabled successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to enabled!");
        }
        return index();
    }

    @RequestMapping(value = "/delete-product/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public ModelAndView deletedProduct(@PathVariable("id") Long id, RedirectAttributes attributes){
        try {
            productService.deleteById(id);
            attributes.addFlashAttribute("success", "Deleted successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to deleted");
        }
        return index();
    }

}
