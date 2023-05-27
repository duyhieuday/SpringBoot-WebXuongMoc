package com.example.admin.presentation.controller.page;


import com.example.admin.presentation.controller.BaseController;
import com.example.admin.presentation.internalmodel.Fragment;
import com.example.admin.presentation.internalmodel.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/page")
public class HomeController extends BaseController {

    @Override
    @RequestMapping
    public ModelAndView index() {
        return home();
    }

    @GetMapping("/home")
    public ModelAndView home() {
        return view(buildContext(Resource.Layout.MasterCommon)
                .setTitle("Home")
                .setContent(Fragment.of(Resource.Layout.SharedCommon))
        );
    }

}
