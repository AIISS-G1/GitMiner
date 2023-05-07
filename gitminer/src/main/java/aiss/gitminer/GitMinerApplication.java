package aiss.gitminer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@RequestMapping("/gitminer")
public class GitMinerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GitMinerApplication.class, args);
	}
}
