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
                git branch: 'release/CT-184',
                credentialsId: 'jenkins-github',
                url: 'https://github.com/Xeat-KEA/CodeLLMService.git'

            }
        }

        stage('Use and Copy .env') {
            steps {
                // Credentials로부터 .env 파일 가져오기
                withCredentials([file(credentialsId: 'env-file-24-12-02', variable: 'ENV_FILE')]) {
                    sh '''
                    echo "Using .env file from $ENV_FILE"
                    cat $ENV_FILE
                    cp $ENV_FILE src/main/resources/.env  # .env 파일을 github 코드 빌드 전 프로젝트 디렉토리로 복사
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
                    ./gradlew clean build -Dspring.profiles.active=prod
                '''
            }
        }
        stage('Build and Push Docker Image') {
            steps {
                script {
                    // 도커 이미지 빌드, 도커 허브로 푸시
                    sh 'docker build --build-arg BASE_IMAGE=openjdk:17 --build-arg JAR_FILE=build/libs/LLMService-0.0.1-SNAPSHOT.jar -t ${IMAGE_NAME}:latest .'
                    withCredentials([usernamePassword(credentialsId: 'docker_credential_id', usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD')]) {
                        docker.withRegistry('https://index.docker.io/v1/', 'docker_credential_id') {
                            sh '''
                                echo "$DOCKERHUB_PASSWORD" | docker login -u "$DOCKERHUB_USERNAME" --password-stdin
                                docker push "${IMAGE_NAME}:latest"
                            '''
                        }
                    }
                }
            }
        }

        stage('Run Docker Container') {
            steps {
                script {
                    sh '''
                        echo "기존 컨테이너 종료"
                        docker rm -f llmservice || true
                        echo "컨테이너 실행 시작"
                        docker run -d --name llmservice -p 8080:8080 ${IMAGE_NAME}:latest
                    '''
                }
            }
        }
    }
}