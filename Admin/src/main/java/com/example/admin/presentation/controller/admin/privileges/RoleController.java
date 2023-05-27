package com.example.admin.presentation.controller.admin.privileges;

import com.example.admin.presentation.controller.BaseController;
import com.example.admin.presentation.internalmodel.Fragment;
import com.example.admin.presentation.internalmodel.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/privileges/role")
public class RoleController extends BaseController {

    @Override
    @RequestMapping
    public ModelAndView index() {
        return view(buildContext(Resource.Layout.MasterAdmin)
                .setTitle("Role")
                .setContent(Fragment.of("templates/admin/pages/privileges/role.html")));
    }


}
