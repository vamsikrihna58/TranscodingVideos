package com.TranscodeVideoUsingFFMPEG.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TranscodeVideoUsingFFMPEG.Service.TranscodingService;

@RestController
public class ControllerClass {
	@Autowired
	private TranscodingService ts;
	@GetMapping("vamsi")
	public String Trancode(@RequestParam("url") String url) {
		return ts.videoResolution(url);
		
	}

}
