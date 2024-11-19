pipeline {
    agent any
    tools {
        gradle 'gradle'
    }

    environment {
        IMAGE_NAME = "hurraypersimmon/llmservice"  // Docker Hub ID와 리포지토리 이름
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'develop', url: 'https://github.com/Xeat-KEA/LLMService.git'
            }
        }

        stage('Build Gradle Project') {
            steps {
                // Gradle 실행 권한 부여
                sh '''
                    echo 'gradlew 빌드 시작'
                    chmod +x ./gradlew
                    ./gradlew clean build
                '''
            }
        }


}