package com.wafersystems.mrbs.web.integrate.surgery;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("surgery")
public class SurgeryController {

	@RequestMapping("index")
	public String index()throws Exception{
		return "integrate/surgery_index";
	}
}
