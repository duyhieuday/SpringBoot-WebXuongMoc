package com.example.admin.presentation.controller.admin;


import com.example.admin.presentation.controller.BaseController;
import com.example.admin.presentation.internalmodel.Fragment;
import com.example.admin.presentation.internalmodel.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class DashboardController extends BaseController {

    @Override
    @RequestMapping
    public ModelAndView index() {
        return new ModelAndView("redirect:/admin/dashboard");
    }

    @RequestMapping("/dashboard")
    public ModelAndView home() {
        return view(buildContext(Resource.Layout.MasterAdmin)
                .setTitle("Dashboard")
                .setContent(Fragment.of("templates/admin/pages/home/dashboard.html")));
    }

}
