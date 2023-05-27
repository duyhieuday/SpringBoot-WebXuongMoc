package com.example.admin.presentation.controller;


import com.example.admin.presentation.internalmodel.Context;
import com.example.admin.presentation.internalmodel.Resource;
import org.springframework.data.util.Pair;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotNull;

public abstract class BaseController {

    private static final String PARAMETER_AJAX = "ajax";
    private static final String ATTR_CONTEXT = "context";

    public abstract ModelAndView index();

    @NotNull
    @SafeVarargs
    protected final ModelAndView view(Context context, Pair<String, Object>... data) {
        return view(new ModelAndView(), context, data);
    }

    @NotNull
    @SafeVarargs
    protected final ModelAndView view(@NotNull ModelAndView source, Context context, Pair<String, Object>... data) {
        if (context == null) {
            context = buildContext(Resource.Layout.MasterCommon);
        }
        if (source.getViewName() == null) {
            source.setViewName(context.getLayout());
        }
        if (data != null) for (Pair<String, Object> pair : data) {
            source.addObject(pair.getFirst(), pair.getSecond());
        }
        if (isAjaxRequest()) {
            context.setAjax(true);
            context.setLayout(Resource.Layout.MasterAjax);
            source.setViewName(context.getLayout());
        }
        source.addObject(ATTR_CONTEXT, context);
        return source;
    }

    protected Context buildContext(Resource.Layout layout) {
        return Context.builder(layout).setIcon(Resource.Icon.AppLogo);
    }

    protected static boolean isAjaxRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            return attributes.getRequest().getParameter(PARAMETER_AJAX) != null;
        }
        return false;
    }
}
