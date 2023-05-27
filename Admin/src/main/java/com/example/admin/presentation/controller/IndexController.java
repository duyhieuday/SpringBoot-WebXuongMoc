package com.example.admin.presentation.controller;


import com.example.admin.presentation.internalmodel.Fragment;
import com.example.admin.presentation.internalmodel.Resource;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

@Controller
public class IndexController extends BaseController implements ErrorController {

    @Override
    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("redirect:/page");
    }

    @RequestMapping("${server.error.path}")
    public ModelAndView handleError(@NotNull HttpServletRequest request) {
        List<HttpStatus> httpStatuses = Arrays.asList(
                HttpStatus.FORBIDDEN,
                HttpStatus.NOT_FOUND,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus status = HttpStatus.valueOf(statusCode);
        if (!httpStatuses.contains(status)) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return view(buildContext(Resource.Layout.MasterCommon)
                .setPrefix("")
                .setTitle("Error " + status)
                .setContent(Fragment.of("templates/common/errors.html", status.name()))
        );
    }
}
