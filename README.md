
# InsideSoftwaresCommons


Projeto contem todas as classes, métodos e recursos comuns a todos os projetos necessário para integração dos sistemas da Inside Softwares
* Versão disponivel: 1.0-SNAPSHOT
* [License](LICENSE.MD)

## Framework Utilizado

* [Spring Boot](https://spring.io/projects/spring-boot)
  * Versão: 3.1.1
* [Java](https://www.java.com/pt-BR/)
  * Versão: 17 ou superior

## Usado pelos projetos

Esse projeto é usado pelas seguintes projetos:

- [InsideSoftwaresSecurityCommons](https://github.com/InsideSoftwares/InsideSoftwaresSecurityCommons)

## Build do projeto

  * Realizar o clone do projeto
  * Na pasta do clone rodar o seguinte comando ``` mvn clean install ``` projeto ira buildar

## Utilização em demais projetos

  * Importa no pom do projeto Spring: 
  ```xml
    <dependency>
        <groupId>br.com.insidesoftwares.commons</groupId>
        <artifactId>utils</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
  ```

  * Será necessário criar uma configuração(@Bean) no projeto para carregar as mensagens dos erros, como mostrado logo abaixo:
  ```java
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(
                "classpath:messages_commons",
        );
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(10);
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }
  ```
  * Será necessário também criar uma classe de configuração para ativar os logs de cada chamada que o Rest receber:
  ```java
    @RestControllerAdvice
    public class AuthenticationRestAdvice extends InsideSoftwaresRestAdvice {
        private static final String PACKAGE_CONTROLLER = "br.com.insidesoftwares.authenticator.controller";

        public AuthenticationRestAdvice() {
            super(PACKAGE_CONTROLLER);
        }
    }
  ```
  * Para utilizar o cache deve adicionar as seguintes properties por serviço:
  ```yaml
  spring
    data:
      redis:
        repositories:
          enabled: false
        port: 26379
        passowrd: '{cipher}5e954244f702180bb7165fdbf3d89aa7779da7ffa541934a2474dd6844d72065'
        sentinel:
          database: 0
          master: mymaster
          nodes: localhost
          passowrd: '{cipher}5e954244f702180bb7165fdbf3d89aa7779da7ffa541934a2474dd6844d72065'
  
    cache:
      enable: false
      type: redis
      redis:
        key-prefix: 'api::'
        time-to-live: 12000000
  
  insidesoftwares-cache:  
    config:
      timeToLiveSeconds: 2000
      caches:
        - name: INSIDE_ACCESS_USER
          timeToLiveSeconds: 3600
  ```
  * Para habilitar o exibição dos Header, Request Body e Response Body realizar configuração das seguintes properties
  ```yaml
    insidesoftwares:
      filter:
        show-request-body: true
        show-request-headers: true
        show-response-body: true
  ```

# InsideSoftwaresExceptionCommons

Projeto por manter as configurações, tratamento e padrões das Exception ocorrida nos projetos que importa.
* Versão disponivel: 1.0-SNAPSHOT

## Utilização em demais projetos

* Importa no pom do projeto Spring:
  ```xml
    <dependency>
        <groupId>br.com.insidesoftwares.commons</groupId>
        <artifactId>exception</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
  ```

* Será necessário criar uma configuração(@Bean) no projeto para carregar as mensagens dos erros, como mostrado logo abaixo:
  ```java
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(
                "classpath:messages_exception"
        );
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(10);
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }
  ```

# InsideSoftwaresAudit

Projeto por realizar o salvamento de log de execução dos metodos, podendo salvar no mesmo banco de dados da aplicação ou
banco de dados exclusivo para o log.

* Versão disponivel: 1.0-SNAPSHOT

## Bancos suportados

* Mysql: 8.0.34 ou superior
* Postgres: 15.4 ou superior

## Utilização em demais projetos

* Importa no pom do projeto Spring:
  ```xml
    <dependency>
        <groupId>br.com.insidesoftwares.commons</groupId>
        <artifactId>audit</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
  ```
* Para utilizar o log deve adicionar a seguinte properties :
  ```yaml
  insidesoftwares:
    audit:
      system: SYSTEM_NAME
      enable: true
  
  spring:
    datasource:
      audit:
        url: jdbc:databasename
        username: username
        password: password
        driver-class-name: driver_database
        hikari:
          connection-timeout: 5000
          idle-timeout: 150000
          max-lifetime: 300000
          minimum-idle: 10
          maximum-pool-size: 50
  ```
  Para desativar o log basta informar o `enable` como `false` 
* E anotar os metodos para começar a realizar salvamento de logs:
```java
    @InsideAudit(method = "Nome do metodo (Opcional)", description = "Descrição do Metodo (Opcional)")
  ```
