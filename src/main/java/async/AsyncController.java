package async;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestOperations;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class AsyncController {
    private final AsyncRestOperations restOperations = new AsyncRestTemplate();

    static class Response extends HashMap<String, String> {
    }

    @RequestMapping("/")
    public Response github() throws ExecutionException, InterruptedException {
        final Future<ResponseEntity<Response>> future = restOperations.getForEntity("https://api.github.com", Response.class);
        return future.get().getBody();
    }
}
