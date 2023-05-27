package com.example.admin.presentation.controller.admin.privileges;

import com.example.admin.presentation.controller.BaseController;
import com.example.admin.presentation.internalmodel.Fragment;
import com.example.admin.presentation.internalmodel.Resource;
import com.example.library.model.Category;
import com.example.library.repository.CategoryRepository;
import com.example.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin/privileges/categories")
public class CategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository repository;

    @Override
    @RequestMapping
    public ModelAndView index() {
        List<Category> categories = categoryService.findAll();
        return view(buildContext(Resource.Layout.MasterAdmin)
                .setTitle("Categories")
                .setContent(Fragment.of("templates/admin/pages/privileges/categories.html")))
                .addObject("categories", repository.findAll() )
                .addObject("categoryNew", new Category())
                .addObject("categories", categories);

    }

//    @GetMapping("/categories")
//    public ModelAndView categories(Model model, Principal principal){
//        if(principal == null){
//            return index();
//        }
//        List<Category> categories = categoryService.findAll();
//        model.addAttribute("categories", categories);
//        model.addAttribute("size", categories.size());
//        model.addAttribute("title", "Category");
//        model.addAttribute("categoryNew", new Category());
//        return index();
//    }

    @PostMapping("/add-category")
    public ModelAndView add(@ModelAttribute("categoryNew") Category category, RedirectAttributes attributes){
        try {
            categoryService.save(category);
            attributes.addFlashAttribute("success", "Added successfully");
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to add because duplicate name");
        }
        catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Error server");
        }
        return index();

    }

    @RequestMapping(value = "/findById", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Category findById(Long id){
        return categoryService.findById(id);
    }

    @GetMapping("/update-category")
    public ModelAndView update(Category category, RedirectAttributes attributes){
        try {
            categoryService.update(category);
            attributes.addFlashAttribute("success","Updated successfully");
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to update because duplicate name");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Error server");
        }
        return index();
    }

    @RequestMapping(value = "/delete-category", method = {RequestMethod.PUT, RequestMethod.GET})
    public ModelAndView delete(Long id, RedirectAttributes attributes){
        try {
            categoryService.deleteById(id);
            attributes.addFlashAttribute("success", "Deleted successfully");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to deleted");
        }
        return index();
    }

    @RequestMapping(value = "/enable-category", method = {RequestMethod.PUT, RequestMethod.GET})
    public ModelAndView enable(Long id, RedirectAttributes attributes){
        try {
            categoryService.enabledById(id);
            attributes.addFlashAttribute("success", "Enabled successfully");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to enabled");
        }
        return index();
    }


}