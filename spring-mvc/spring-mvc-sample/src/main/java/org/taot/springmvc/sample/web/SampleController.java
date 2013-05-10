package org.taot.springmvc.sample.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/sample")
public class SampleController {
  
  private Logger logger = LoggerFactory.getLogger(getClass());

  @RequestMapping(value="upload", method=RequestMethod.GET)
  public String upload(Model model, HttpServletRequest request) {
    return "/sample/upload";
  }
  
  @RequestMapping(value="upload", method=RequestMethod.POST)
  public ModelAndView uploadSubmit(Model model, HttpServletRequest request) throws IOException {
    
    logger.info("Uploading file");
    
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
    Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
    if (fileMap.isEmpty()) {
      throw new RuntimeException("No file is uploaded.");
    }
    
    File tmpDir = new File(System.getProperty("java.io.tmpdir"));
    File destDir = new File(tmpDir, "upload");
    destDir.mkdir();
    
    List<String> uploaded = new ArrayList<String>();
    for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
//      String name = entry.getKey();
      MultipartFile mFile = entry.getValue();
      String filename = mFile.getOriginalFilename();
      InputStream is = mFile.getInputStream();
      FileOutputStream fos = new FileOutputStream(new File(destDir, filename));
      FileCopyUtils.copy(is, fos);
      uploaded.add(filename);
    }
    
    ModelAndView mav = new ModelAndView("/sample/upload_result");
    mav.addObject("uploaded", uploaded);
    return mav;
  }
  
}
