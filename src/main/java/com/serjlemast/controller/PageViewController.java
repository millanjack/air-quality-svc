package com.serjlemast.controller;

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

  @GetMapping(value = "/statistics")
  public ModelAndView handleStatisticsRedirect() {
    return new ModelAndView(REDIRECT);
  }
}
