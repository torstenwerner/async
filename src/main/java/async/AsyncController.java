package async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestOperations;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@RestController
public class AsyncController {
    final Logger logger = LoggerFactory.getLogger(getClass());

    private final AsyncRestOperations restOperations = new AsyncRestTemplate();

    static class Response extends HashMap<String, String> {
    }

    @RequestMapping("/")
    public DeferredResult<Response> github() throws ExecutionException, InterruptedException {
        final DeferredResult<Response> deferredResult = new DeferredResult<>();
        final ListenableFuture<ResponseEntity<Response>> future =
                restOperations.getForEntity("https://api.github.com", Response.class);
        final SuccessCallback<ResponseEntity<Response>> success = response -> deferredResult.setResult(response.getBody());
        final FailureCallback failure = throwable -> logger.error("error", throwable);
        future.addCallback(success, failure);
        return deferredResult;
    }
}
