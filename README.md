# 🧠 Intelligent Web Application with Angular, Spring Boot, Spring AI & Ollama

This project is a full-stack web application that combines traditional web technologies with advanced AI capabilities. It enables users to interact intelligently with PDF documents through a conversational interface powered by a Large Language Model (LLM).

---

## 🚀 Features

- **Responsive Frontend**: Built with Angular for a dynamic and user-friendly interface.
- **Robust Backend**: Developed using Spring Boot to handle business logic and expose RESTful APIs.
- **AI Orchestration**: Utilizes **Spring AI** to manage connections and workflows between services.
- **LLM Integration**: Employs **Ollama** as the local or remote Large Language Model for processing and generating responses.
- **Retrieval-Augmented Generation (RAG)**:
  - Splits uploaded PDF files into semantic **chunks**.
  - Generates **vector embeddings** for each chunk.
  - Performs efficient similarity search to retrieve relevant chunks.
  - Augments prompts with retrieved context to improve answer relevance.
- **Custom Tools**: Integrates domain-specific tools (e.g., data querying, file analysis) to extend LLM capabilities.

---

## 🔧 Technology Stack

| Layer       | Technology                       |
|-------------|----------------------------------|
| Frontend    | Angular                          |
| Backend     | Spring Boot                      |
| AI Platform | Spring AI                        |
| LLM         | Ollama                           |
| Embedding   | Vector store (e.g., Pinecone, Faiss, or local) |
| Database    | [PostgreSQL] (Not yet)      |

---
