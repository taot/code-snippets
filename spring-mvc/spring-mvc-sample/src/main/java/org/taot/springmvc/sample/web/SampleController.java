package org.taot.springmvc.sample.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/sample")
public class SampleController {

  @RequestMapping(value="upload", method=RequestMethod.GET)
  public String upload(Model model, HttpServletRequest request) {
    return "/sample/upload";
  }
}
