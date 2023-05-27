package com.example.admin.presentation.controller.admin.settings;


import com.example.admin.presentation.controller.BaseController;
import com.example.admin.presentation.internalmodel.Fragment;
import com.example.admin.presentation.internalmodel.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/settings")
public class SettingsController extends BaseController {

    @Override
    @RequestMapping
    public ModelAndView index() {
        return view(buildContext(Resource.Layout.MasterAdmin)
                .setTitle("Setting")
                .setContent(Fragment.of("templates/admin/pages/settings/settings.html")));
    }

}
