# Work-Finder
President Tech award hackaton

# Описание
Рабочее название проекта - WorkFinder. Маркетинговое - Career Canvas. Проект представляет собой рекомендательную систему для поиска рабочих вакансий и обучающих курсов, выполненную в виде клиент серверного приложения. 
Особенность проекта - задействование нейросетевой модели SBERT large для выполнения семантического анализа профиля пользователя и базы данных вакансий и алгоритма выбора наиболее востребованных специализаций. В результате пользователь получает умный поиск по соответствию личным предпочтениям, а не поиск по ключевым словам. Помимо рекомендаций, приложение может полностью сгенерировать структурированное резюме в docx формате, используя данные из профиля пользователя и его фотографию.  

Проект был разработан на хакатоне President Tech Award: https://awards.gov.uz/

# Детали реализации
После получения данных пользователя (данные содержат в том числе предпочитаемый вид работы: умственный\физический, режим работы: удалённый\гибридный\в офисе, неформализованные предпочтения пользователя: свободный ввод) алгоритм анализирует базу данных вакансий и извлекает специальности (требуемые в вакансиях), наиболее подходящие пользователю. Далее эти специальности ранжируются по популярности и рекомендуются пользователю в уже ранжированном виде. Первая рекомендация специальности записывается в профиль пользователя. Предполагается, что пользователь в дальнейшем может выбрать любую из рекомендаций.

После завершения составления профиля пользователя система может осуществить семантический анализ вакансий из базы данных (с использованием профиля пользователя с уже имеющимися рекомендованными специализациями) и предложить наиболее релевантные результаты.
Поиск курсов осуществляется по рекомендованной специализации.

За генерацию резюме отвечает GPT 3.5 turbo.

Мобильное приложение выполнено в соответствии современным стандартам дизайна и предоставляет понятный, простой и удобный интерфейс для пользователя. Переходы между экранами анимированы.

Для написания серверной части системы использовался Python+FastAPI. Мобильное приложение написано на Kotlin+Jetpack Compose. Для хранения данных пользователей используется sqlalchemy+SQLite, база данных вакансий хранится в векторизованном виде внутри JSON файла. Авторизация пользователей задействует механизм выдачи JWT токенов. Запросы к рекомендательной системе недоступны для неавторизованных пользователей. Серверная часть размещена на AWS EC2 хостинге.
Источником БД вакансий является hh.uz, источником обучающих курсов - Coursera. При проектировании преследовалась цель сделать систему масштабируемой и иметь возможность подключить другие источники данных.

# Установка
Серверная часть может быть собрана в docker образ (соответствующий докер-файл есть в репозитории) и размещена на любой машине с доступом к не менее 4ГБ ОЗУ и 30 ГБ свободного пространства на жёстком диске. 
При запуске контейнера необходимо указать такие переменные среды, как:

GOOGLE_TRANSLATE_API_KEY - путь к json файлу, необходим для работы языкового перевода.

OPENAI_API_KEY - текстовое значение, необходимо для работы генерации резюме.

OPENAI_ORGANIZATION_KEY - текстовое значение, необходимо для работы генерации резюме.


и порты port:8080

После запуска сервера можно посмотреть документацию по адресу http://ip.add.re.ss:8080/docs.
Там же можно проверить работоспособность всех запросов и посмотреть использующиеся модели данных. 
APK файл мобильного приложения есть в репозитории, но адрес webAPI сервера вшит в исходный код.

В случае надобности посмотреть приложение обращайтесь в issues или в телеграм @SherAlex1998.


# Work-Finder
President Tech award hackaton

# Description
The working name of the project is WorkFinder. Marketing - Career Canvas. The project is a recommendation system for finding job vacancies and training courses, made in the form of a client-server application.
The peculiarity of the project is the use of the SBERT large neural network model to perform semantic analysis of the user profile and the database of vacancies and the algorithm for selecting the most popular specializations. As a result, the user gets a smart search based on personal preferences, rather than a keyword search. In addition to recommendations, the application can fully generate a structured resume in docx format using data from the user's profile and his photo.

The project was developed at the President Tech Award hackathon: https://awards.gov.uz/

# Implementation Details
After receiving the user's data (the data contains, among other things, the preferred type of work: mental \physical, work mode: remote\hybrid\ in the office, informal user preferences: free input), the algorithm analyzes the database of vacancies and extracts the specialties (required in vacancies) that are most suitable for the user. Further, these specialties are ranked by popularity and are recommended to the user in an already ranked form. The first recommendation of the specialty is recorded in the user profile. It is assumed that the user can choose any of the recommendations in the future.
After completing the user profile, the system can perform a semantic analysis of vacancies from the database (using a user profile with already existing recommended specializations) and offer the most relevant results.
The search for courses is carried out according to the recommended specialization.
GPT 3.5 turbo is responsible for resume generation.
The mobile application is made in accordance with modern design standards and provides a clear, simple and user-friendly interface for the user. Animations are used for views changing.

Python+FastAPI was used to write the server part of the system. The mobile application is written in Kotlin+Jetpack Compose. sqlalchemy+SQLite is used to store user data, the job database is stored in a vectorized form inside a JSON file. User authorization uses the mechanism for issuing JWT tokens. Requests to the recommendation system are not available to unauthorized users. The server part is hosted on AWS EC2 hosting.
The source of the job database is hh.uz , the source of training courses is Coursera. When designing, the goal was to make the system scalable and be able to connect other data sources.

# Installation
The server part can be assembled into a docker image (the corresponding docker file is in the repository) and placed on any machine with access to at least 4 GB of RAM and 30 GB of free hard disk space.
When starting the container, you must specify environment variables such as:

GOOGLE_TRANSLATE_API_KEY is the path to the json file required for the language translation to work.

OPENAI_API_KEY is a text value required for resume generation.

OPENAI_ORGANIZATION_KEY is a text value required for resume generation.

and ports port:8080

After starting the server, you can view the documentation at http://ip.add.re.ss:8080/docs .
There you can also check the operability of all queries and see the data models used.
The APK file of the mobile application is in the repository, but the address of the WebAPI server is sewn into the source code.
If you need to view the application, please contact issues or telegram @SherAlex1998.
