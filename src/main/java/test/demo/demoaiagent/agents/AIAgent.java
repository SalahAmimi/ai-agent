package test.demo.demoaiagent.agents;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import test.demo.demoaiagent.tools.AgentTools;

@Service
public class AIAgent {

    private ChatClient chatClient;

    public AIAgent(ChatClient.Builder chatClient, ChatMemory chatMemory,
                   AgentTools agentTools, SimpleVectorStore vectorStore) {
        this.chatClient = chatClient
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory)
                                .build()
                )
                .defaultTools(agentTools)
                //.defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore))
                .build();
    }


    public String askAgent(@RequestParam(defaultValue = "Hello") String query) {
        return  chatClient.prompt()
                .user(query)
                .call().content();
    }

    public Flux<String> askAgentStream(@RequestParam(defaultValue = "Hello") String query) {
        return  chatClient.prompt()
                .user(query)
                .stream().content();
    }
}
