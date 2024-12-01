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
                git branch: 'release/CT-184', url: 'https://github.com/Xeat-KEA/LLMService.git'
            }
        }

        stage('Use .env') {
            steps {
                // Credentials로부터 .env 파일 가져오기
                withCredentials([file(credentialsId: 'env-file', variable: 'ENV_FILE')]) {
                    sh '''
                    echo "Using .env file from $ENV_FILE"
                    cat $ENV_FILE
                    '''
                }
            }
        }

        stage('Build Gradle Project') {
            steps {
                // Gradle 실행 권한 부여 및 빌드 실행
                sh '''
                    echo 'gradlew 빌드 시작'
                    chmod +x ./gradlew
                    ./gradlew clean build
                '''
            }
        }
    }
}