# Order Management System - backend

## 0. Подготовка
1. Если хотите запустить сервер с уже подготовленными тестовыми данными, измените в файле `src\main\resources\application.yml` активный профиль с `production` на `development`.
2. Чтобы изменить используемую бд, исправьте `datasource` в том же файле

Для сборки сервера необходимо иметь установленный [maven](https://maven.apache.org).

## 1. Сборка
Из корневой директории выполните команду `mvn package`

## 2. Запуск

Перейдите в директорию `target` и выполните `java -jar backend-1.0.0.jar`
