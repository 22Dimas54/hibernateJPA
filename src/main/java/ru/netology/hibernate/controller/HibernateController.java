package ru.netology.hibernate.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.security.RolesAllowed;

@RestController
public class HibernateController {
    @GetMapping("/read")
    @Secured({"ROLE_READ"})
    public String read() {
        return "read";
    }

    @GetMapping("/write")
    @RolesAllowed({"ROLE_WRITE"})
    public String write() {
        return "write";
    }

    @GetMapping("/pre")
    @PreAuthorize("hasAnyRole('ROLE_WRITE','ROLE_DELETE')")
    public String pre() {
        return "pre";
    }

    @GetMapping("/post")
    @PostAuthorize("hasAnyRole('ROLE_WRITE','ROLE_DELETE')")
    public String post() {
        return "post";
    }

    @GetMapping("/preAuthorize")
    @PreAuthorize("#username == authentication.principal.username")
    public String preAuthorize(String username) {
        return "username: " + username;
    }
}
