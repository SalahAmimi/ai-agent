package test.demo.demoaiagent;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoAiAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoAiAgentApplication.class, args);
    }

    @Bean
    public SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel){
        return SimpleVectorStore.builder(embeddingModel).build();

    }
}
