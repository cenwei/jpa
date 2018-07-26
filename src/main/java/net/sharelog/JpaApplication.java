package net.sharelog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sharelog.service.IUserService;


@Controller
@SpringBootApplication
public class JpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}
	
	@ResponseBody
	@GetMapping("/")
	public String hello() {
		return "Hello World!";
	}
	
	@Autowired
	IUserService userService;
	
	@RequestMapping("db")
	public String testDB() {
		userService.register();
		return "db register test was finished.";
	}
}
