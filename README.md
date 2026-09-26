# Spring AI in Action

Hands-on implementations, experiments, and notes while learning **Spring AI** through the book *Spring AI in Action*.

This repository explores how AI capabilities can be integrated into **Spring Boot applications**, using **Spring AI** and **Ollama** to work with local LLMs.

## Tech Stack

* **Java**
* **Spring Boot**
* **Spring AI**
* **Ollama**
* **Gradle**
* **REST APIs**

## About This Repository

This repository contains examples, implementations, and experiments created while working through *Spring AI in Action*.

The goal is to understand how to build AI-powered applications using the Spring ecosystem and gain practical experience integrating LLMs into Java backend applications.

## Ollama

The examples in this repository use **Ollama** to run LLMs locally.

Install Ollama and make sure it is running before executing the applications.

For example:

```bash
ollama pull qwen3:8b
```

Check the installed models:

```bash
ollama list
```

You can use any Ollama model supported by the particular example. The required model may vary between projects.

## Project Structure

Examples are organized according to the concepts and chapters being explored.

```text
spring-ai-in-action/
│
├── ch01/
├── ch02/
├── ch03/
├── ...
│
└── README.md
```

The project structure may evolve as I progress through the book.

## Running the Projects

Clone the repository:

```bash
git clone https://github.com/arpanduari/spring-ai-in-action-playground
cd spring-ai-in-action
```

Make sure Ollama is installed and running.

Pull the model required by the example:

```bash
ollama pull qwen3:8b
```

### Run with Gradle

On macOS/Linux:

```bash
./gradlew bootRun
```

On Windows:

```bash
gradlew.bat bootRun
```

### Build the Project

On macOS/Linux:

```bash
./gradlew clean build
```

On Windows:

```bash
gradlew.bat clean build
```

The generated JAR can then be run with:

```bash
java -jar build/libs/*.jar
```

## Configuration

Depending on the example, Spring AI may need to be configured to communicate with Ollama.

A typical configuration may look like:

```yaml
spring:
    ai:
        ollama:
            base-url: http://localhost:11434
            chat:
                model: qwen3:8b

```

The exact configuration can vary depending on the Spring AI version and the example being implemented.

## Disclaimer

All examples and implementations in this repository are based on concepts and examples from the book ***Spring AI in Action*** and are shared **solely for learning and educational purposes**.

This repository is **not affiliated with, endorsed by, or an official companion repository** of the book or its authors.

The code may include my own modifications, experiments, and adaptations made while learning and understanding the concepts presented in the book.

## Why This Repository?

I'm using this repository to document my learning journey with **Spring AI** and to build practical experience integrating AI capabilities into Java and Spring Boot applications.

The focus is not just on making the examples work, but on understanding **how Spring AI works internally and how these concepts can be applied to real-world backend applications**.

---
