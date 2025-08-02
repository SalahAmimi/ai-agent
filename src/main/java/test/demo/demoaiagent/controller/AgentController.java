package test.demo.demoaiagent.controller;

import lombok.AllArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import test.demo.demoaiagent.agents.AIAgent;
import test.demo.demoaiagent.rag.DocumentIndexor;

import java.io.IOException;

@RestController
@CrossOrigin("*")

public class AgentController {

    private AIAgent agent;
    private DocumentIndexor indexor;

    public AgentController(AIAgent agent,DocumentIndexor indexor){
        this.agent=agent;
        this.indexor=indexor;
    }

    @GetMapping(value = "/askAgent", produces = MediaType.TEXT_PLAIN_VALUE)
    public String onQuery(@RequestParam(defaultValue = "Hello") String query) {
        return  agent.askAgent(query);
    }

    @GetMapping(value = "/askAgent/stream", produces = MediaType.TEXT_PLAIN_VALUE)
    public Flux<String> onQueryStream(@RequestParam(defaultValue = "Hello") String query) {
        return  agent.askAgentStream(query);
    }

//    @PostMapping(value = "/loadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> loadFile(@RequestPart("file") MultipartFile file) throws IOException {
//        indexor.loadFile(file);
//        return ResponseEntity.ok("");
//    }
@PostMapping(value = "/loadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<String> loadFile(@RequestPart("file") MultipartFile file) {
    try {
        indexor.loadFile(file);
        return ResponseEntity.ok("File uploaded");
    } catch (IOException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
    }
}


}
