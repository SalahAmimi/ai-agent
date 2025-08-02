package test.demo.demoaiagent.rag;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Service
public class DocumentIndexor {
    @Value("classpath:/pdfs/cv.pdf")
    private Resource pdfResource;
    @Value("store.json")
    private String fileStore;
    private VectorStore vectorStore;

    public DocumentIndexor(SimpleVectorStore vectorStore){
        this.vectorStore=vectorStore;
    }

   // @Bean
    public SimpleVectorStore getvectoreStore(EmbeddingModel embeddingModel){
        SimpleVectorStore vectorStore = SimpleVectorStore.builder(embeddingModel).build();
        Path path = Path.of("src", "main", "resources", "pdfs");
        File file = new File(path.toFile(), fileStore);
        if(!file.exists()){
            PagePdfDocumentReader pdfDocumentReader =
                    new PagePdfDocumentReader(pdfResource);
            List<Document> documents = pdfDocumentReader.get();
            TextSplitter textSplitter = new TokenTextSplitter();
            List<Document> chunks = textSplitter.apply(documents);
            vectorStore.add(chunks);
            vectorStore.save(file);
        }else {
            vectorStore.load(file);
        }
        return vectorStore;
    }


    public void loadFile(MultipartFile pdfFile) throws IOException {
        //SimpleVectorStore vectorStore = SimpleVectorStore.builder(embeddingModel).build();
        Path path = Path.of("src", "main", "resources", "pdfs");
        File file = new File(path.toFile(), fileStore);
        if(file.exists()){
            ((SimpleVectorStore) vectorStore).load(file);
        }

        PagePdfDocumentReader pdfDocumentReader =
                new PagePdfDocumentReader(pdfFile.getResource());
        List<Document> documents = pdfDocumentReader.get();
        TextSplitter textSplitter = new TokenTextSplitter();
        List<Document> chunks = textSplitter.apply(documents);
        vectorStore.add(chunks);
        ((SimpleVectorStore) vectorStore).save(file);
    }
}
