package com.xfrenzy47x.controller;

import com.xfrenzy47x.model.CodePlatformPage;
import com.xfrenzy47x.service.CodePlatformPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class HtmlController {
    Logger logger = Logger.getLogger(HtmlController.class.getName());

    @Autowired
    CodePlatformPageService pageService;

    @GetMapping("/code/{id}")
    public String getHTMLCode(@PathVariable String id, Model model) {
        logger.log(Level.INFO,"getHtmlCode() : {0}", id);
        CodePlatformPage page = pageService.get(id);

        if (page == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        logger.log(Level.INFO, "getHTMLCode() : {0}", page);

        model.addAttribute("date", page.getDate());
        model.addAttribute("code", page.getCode());

        boolean time = false;
        boolean views = false;
        if (page.getTimeRestricted() || page.getViewsRestricted()) {
            if (page.getTimeRestricted()) {
                model.addAttribute("time", page.getTime());
                time = true;
            }
            if (page.getViewsRestricted()) {
                model.addAttribute("views", page.getViews());
                views = true;
            }

            String view = time && views ? "getSecretCodeBoth" : "";
            view = view.isEmpty() && time ? "getSecretCodeTime" : view;
            view = view.isEmpty() && views ? "getSecretCodeViews" : view;

            return view;
        }

        return "getSingleCode";
    }

    @GetMapping("/code/latest")
    public String getLatestCodes(Model model) {
        logger.log(Level.INFO,"getLatestCodes()");
        List<CodePlatformPage> pages = pageService.getLatest();
        model.addAttribute("pages", pages);
        return "getLatestCodes";
    }

    @GetMapping("/code/new")
    public String create(Model model) {
        logger.log(Level.INFO,"getNewCodePage()");
        return "create";
    }
}
