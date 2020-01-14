	Инструкция по работе с приложением:
1. Скачайте репозиторий в удобную для Вас директорию. 
2. Распакуйте его.
3. Укажите данные директории, в которой вы распаковали архив, в файле application.properties:
upload.path = {DirPath}/ImgStorage/img_uploads/ - где, DirPath это путь к вашей дириктории
4. Создайте базу данных PostgreSQL.
5. Укажите данные своей БД в файле application.properties:
spring.datasource.url - host вашей БД
spring.datasource.username - username для подключения к БД
spring.datasource.password - password для подключения к БД
6. Соберите приложение.
7. С помощью любого инструмента, для тестирования REST API приложений, отправляйте запросы на URL: http://localhost:8080/img/.
Варианты запросов: 
GET - http://localhost:8080/img/{ImgName}

GET - http://localhost:8080/img/preview/{ImgName}

POST - http://localhost:8080/img/byUrl 

	body - массив ссылок на скачивание картинок. Например : 
		[
			"https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png"
		]
		
8. Картинки будут храниться в папках img_uploads и img_uploads/preview.

	Инструкция по работе с тестами:
	
1. Создайте отдельную базу данных PostgreSQL для тестов.
2. Укажите данные своей БД в файле application-test.properties:
spring.datasource.url - host вашей БД
spring.datasource.username - username для подключения к БД
spring.datasource.password - password для подключения к БД
3. Укажите данные директории, в которой вы распаковали архив, в файле application-test.properties:
upload.path = {DirPath}/ImgStorage/img_uploads/ - где, DirPath это путь к вашей дириктории
4. Запустите тесты.
