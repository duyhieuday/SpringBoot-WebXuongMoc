package com.example.admin.presentation.internalmodel;


import javax.validation.constraints.NotNull;

public class Fragment {
    private static final String DEFAULT_FRAGMENT_NAME = "index";
    private String layout;
    private String name;

    @NotNull
    public static Fragment of(@NotNull Resource.Layout layout) {
        return of(layout.getName(), DEFAULT_FRAGMENT_NAME);
    }

    @NotNull
    public static Fragment of(String layout) {
        return of(layout, DEFAULT_FRAGMENT_NAME);
    }

    @NotNull
    public static Fragment of(@NotNull Resource.Layout layout, String name) {
        return of(layout.getName(), name);
    }

    @NotNull
    public static Fragment of(String layout, String name) {
        return new Fragment().setLayout(layout).setName(name);
    }

    private Fragment() {
    }

    public String getLayout() {
        return layout;
    }

    public Fragment setLayout(@NotNull Resource.Layout layout) {
        return setLayout(layout.getName());
    }

    public Fragment setLayout(@NotNull String layout) {
        this.layout = Resource.Layout.verifyLayout(layout);
        return this;
    }

    public String getName() {
        return name;
    }

    public Fragment setName(String name) {
        this.name = name;
        return this;
    }

}
