package async;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsyncController {
    @RequestMapping("/")
    public String hello() {
        return "Hello!";
    }
}
