plugins {
	java
	id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.7"
	id("com.avast.gradle.docker-compose") version "0.16.12" // ✅ 다른 플러그인 사용 (Palantir 아님)
}

group = "com.golden_dobakhe"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.mysql:mysql-connector-j")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	runtimeOnly("com.h2database:h2")
	developmentOnly("org.springframework.boot:spring-boot-docker-compose")
	implementation ("org.springframework.boot:spring-boot-starter-security") //security

}

//// Docker Compose 설정 (정상 작동되는 버전)
dockerCompose {
	useComposeFiles.set(listOf("docker-compose.yml"))
	startedServices.set(listOf("mysql"))
	isRequiredBy(tasks.named("bootRun")) // bootRun 실행 시 Docker Compose 자동 실행
	removeContainers.set(true)
	stopContainers.set(true)
	removeVolumes.set(true)
}

// bootRun 작업 종료 시 Docker Compose 정리
tasks.named("bootRun") {
	finalizedBy("dockerComposeDown") // bootRun 작업 종료 후 dockerComposeDown 실행
}

