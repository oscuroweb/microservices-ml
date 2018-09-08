package oscuroweb.ia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import oscuroweb.ia.service.SparkService;

@RestController
public class MLController {
	
	@Autowired
	SparkService sparkService;
	
	@GetMapping("/version")
	public Long getVersion() {
		return sparkService.getVersion();
	}

	@GetMapping("/allVersions") 
	public List<Long> getAllVersions() {
		return sparkService.getAvailableVersions();
	}
}
