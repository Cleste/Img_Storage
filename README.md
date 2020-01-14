### Инструкция по работе с приложением:
- Скачайте репозиторий в удобную для Вас директорию. 
- Распакуйте его.
- Укажите данные директории, в которой вы распаковали архив, в файле application.properties:
upload.path = {DirPath}/ImgStorage/img_uploads/ - где, DirPath это путь к вашей дириктории
- Создайте базу данных PostgreSQL.
- Укажите данные своей БД в файле application.properties:
spring.datasource.url - host вашей БД
spring.datasource.username - username для подключения к БД
spring.datasource.password - password для подключения к БД
- Соберите приложение.
- С помощью любого инструмента, для тестирования REST API приложений, отправляйте запросы на URL: http://localhost:8080/img/.
Варианты запросов: 

GET - http://localhost:8080/img/{ImgName}

GET - http://localhost:8080/img/preview/{ImgName}

POST - http://localhost:8080/img/byUrl 

	body - массив ссылок на скачивание картинок. Например : 
		[
			"https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png"
		]
- Картинки будут храниться в папках img_uploads и img_uploads/preview

### Инструкция по работе с тестами:	
	
- Создайте отдельную базу данных PostgreSQL для тестов.
- Укажите данные своей БД в файле application-test.properties:
spring.datasource.url - host вашей БД
spring.datasource.username - username для подключения к БД
spring.datasource.password - password для подключения к БД
- Укажите данные директории, в которой вы распаковали архив, в файле application-test.properties:
upload.path = {DirPath}/ImgStorage/img_uploads/ - где, DirPath это путь к вашей дириктории
- Запустите тесты.
