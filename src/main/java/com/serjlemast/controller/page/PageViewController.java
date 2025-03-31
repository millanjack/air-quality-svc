package com.serjlemast.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageViewController {

  public static final String REDIRECT = "redirect:/";

  @GetMapping(value = "/controller")
  public ModelAndView handleControllerRedirect() {
    return new ModelAndView(REDIRECT);
  }

  @GetMapping(value = "/sensors")
  public ModelAndView handleSensorsRedirect() {
    return new ModelAndView(REDIRECT);
  }
}
