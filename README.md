# Limpacity - Worker 

Este worker realiza de tempos em tempos a verificação de coletas não realizadas no banco de dados e dispara novas notificações.

## Requisitos
```sh
Java 14
Docker Compose
Plugin Lombok
```

## Instalação OS X & Linux:

**Java 11 - SDKMAN:**

```sh
https://sdkman.io/install
sdk i java 11.0.2-open
```

**Lombok plugin:**

```sh
Intellij: https://projectlombok.org/setup/intellij
Eclipse : https://projectlombok.org/setup/eclipse
```

## Ambiente

**Acessando as variáveis:**

```
  @Value("${string.profiles.active.env}")
  private String env;
```

## Configuração para Desenvolvimento

Acessar a pasta raiz do projeto:

**Compilar o projeto:**

```sh
sdk use java 11.0.2-open
./mvnw clean package
```

**Executar o coverage:**

```sh
sdk use java 11.0.2-open
./mvnw clean install jacoco:report
```

**Executar o projeto com configuração local:**

```sh
sdk use java 11.0.2-open
./mvnw clean spring-boot:run -Dspring-boot.run.profiles=local
```
