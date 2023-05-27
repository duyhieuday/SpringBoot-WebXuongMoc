package com.example.admin.presentation.controller.admin.auth;


import com.example.admin.presentation.controller.BaseController;
import com.example.admin.presentation.internalmodel.Fragment;
import com.example.admin.presentation.internalmodel.Resource;
import com.example.library.model.LoginError;
import com.example.library.security.jwt.JwtCookiesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

@Controller
@RequestMapping("/admin/auth")
public class AuthController extends BaseController {

    private final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final JwtCookiesManager jwtCookiesManager;

    public AuthController(JwtCookiesManager jwtCookiesManager) {
        this.jwtCookiesManager = jwtCookiesManager;
    }

    @Override
    @RequestMapping
    public ModelAndView index() {
        return new ModelAndView("redirect:/admin/auth/login");
    }

    @GetMapping(value = "/login")
    public ModelAndView login(@NotNull HttpServletRequest request, @RequestParam(value = "error", required = false) String error) {
        if (jwtCookiesManager.getLoginSessionToken(request) != null) {
            return redirect();
        }
        return view(buildContext(Resource.Layout.MasterAdminBlank)
                .setTitle("Login")
                .setContent(Fragment.of("templates/admin/pages/auth/login.html")))
                .addObject("error", LoginError.decode(error));
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return null;
    }

    @GetMapping("/logout")
    public ModelAndView logout() {
        // not handle
        return null;
    }

    @GetMapping("/redirect")
    public ModelAndView redirect() {
        LOGGER.warn("Redirect {}", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return new ModelAndView("redirect:/admin/dashboard");
    }

}
