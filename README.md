## Spring Boot Services: news
Для запуска приложений на машине нужно установить:
- Java 17
- Maven последней версии

### Запустить приложение из командной строки
В корневой директории приложения (news):

`mvn spring-boot:run`

или

`mvn clean install` - выполнить в корневой папке приложения (book-store или book-discount)

`cd target` - перейти в папку target

`java -jar news-0.0.1-SNAPSHOT` - в папке target выполнить команду

### Получить список новостей из браузера

http://localhost:8081/news

### Ссылка на консоль базы данных H2
http://localhost:8081/h2-console

имя пользователя: sa

пароль: 123
