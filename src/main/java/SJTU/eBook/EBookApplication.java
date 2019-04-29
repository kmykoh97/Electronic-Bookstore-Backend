package SJTU.eBook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ServletComponentScan
@SpringBootApplication
public class EBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(EBookApplication.class, args);
	}

}
