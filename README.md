
# InsideSoftwaresCommons


Projeto contem todas as classes, métodos e recursos comuns a todos os projetos necessário para integração dos sistemas da Inside Softwares
* Versão disponivel: 1.0-SNAPSHOT

## Framework Utilizado

* [Spring Boot]('https://spring.io/projects/spring-boot')
  * Versão: 2.7.3
* [Java]('https://www.java.com/pt-BR/')
  * Versão: 17 ou superior

## Usado pelos projetos

Esse projeto é usado pelas seguintes projetos:

- [InsideSoftwaresSecurityCommons]('https://github.com/InsideSoftwares/InsideSoftwaresSecurityCommons') 
- [InsideSoftwaresExceptionCommons]('https://github.com/InsideSoftwares/InsideSoftwaresExceptionCommons') 
- [InsideSoftwaresAuthenticator]('https://github.com/InsideSoftwares/InsideSoftwaresAuthenticator') 
- [InsideSoftwaresAccessControl]('https://github.com/InsideSoftwares/access_control_back')

## Build do projeto

  * Realizar o clone do projeto
  * Na pasta do clone rodar o seguinte comando ``` mvn clean install ``` projeto ira buildar

## Utilização em demais projetos

  * Importa no pom do projeto Spring: 
  ```
    <dependency>
        <groupId>br.com.insidesoftwares</groupId>
        <artifactId>commons</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
  ```

  * Será necessário criar uma configuração(@Bean) no projeto para carregar as mensagens dos erros, como mostrado logo abaixo:
  ```
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
  ```
    @RestControllerAdvice
    public class AuthenticationRestAdvice extends InsideSoftwaresRestAdvice {
        private static final String PACKAGE_CONTROLLER = "br.com.insidesoftwares.authenticator.controller";

        public AuthenticationRestAdvice() {
            super(PACKAGE_CONTROLLER);
        }
    }
  ```