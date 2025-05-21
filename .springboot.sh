#bin/bash!

cd template-springboot

mkdir -p .mvn/wrapper 
touch README.md mvnw.cmd mvnw .gitignore lombok.config pom.xml Jenkinsfile dockerfile
mkdir src/main/java/
mkdir src/main/java/com.singleton.project
# curd singleton repeact
mkdir src/main/java/com.singleton.project/curd
mkdir src/main/java/com.singleton.project/curd/controller
mkdir src/main/java/com.singleton.project/curd/mapper
mkdir src/main/java/com.singleton.project/curd/dto
mkdir src/main/java/com.singleton.project/curd/entity
mkdir src/main/java/com.singleton.project/curd/vo
mkdir src/main/java/com.singleton.project/curd/repository
mkdir src/main/java/com.singleton.project/curd/service
mkdir src/main/java/com.singleton.project/curd/service/impl
# spring base function
mkdir src/main/java/com.singleton.project/foundation
mkdir src/main/java/com.singleton.project/foundation/config
mkdir src/main/java/com.singleton.project/foundation/anotations
mkdir src/main/java/com.singleton.project/foundation/aspect
mkdir src/main/java/com.singleton.project/foundation/enums
mkdir src/main/java/com.singleton.project/foundation/beans
mkdir src/main/java/com.singleton.project/foundation/exception
mkdir src/main/java/com.singleton.project/foundation/interceptor
mkdir src/main/java/com.singleton.project/foundation/filter
# utils
mkdir src/main/java/com.singleton.project/utils


mkdir src/main/resources/
mkdir src/main/resources/truststore
mkdir src/main/resources/sql
mkdir src/main/resources/ddl
mkdir src/main/resources/i18n

mkdir src/main/resources/static
mkdir src/main/resources/templates
touch src/main/resources/application.yml
touch src/main/resources/application-dev.yml
touch src/main/resources/application-prod.yml
touch src/main/resources/application-test.yml
touch src/main/resources/application-staging.yml

