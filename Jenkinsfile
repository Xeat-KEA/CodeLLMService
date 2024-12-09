pipeline {
    agent any
    tools {
        gradle 'gradle'
    }

    environment {
        IMAGE_NAME = "hurraypersimmon/codingtext"  // Docker Hub ID와 리포지토리 이름
        IMAGE_TAG = "codellmservice"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'release/CT-184',
                credentialsId: 'GitHub',
                url: 'https://github.com/Xeat-KEA/CodeLLMService.git'

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
                    sh 'docker build --build-arg BASE_IMAGE=openjdk:17 --build-arg JAR_FILE=build/libs/LLMService-0.0.1-SNAPSHOT.jar -t ${IMAGE_NAME}:${IMAGE_TAG} .'
                    withCredentials([usernamePassword(credentialsId: 'ct-dockerhub', usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD')]) {
                        docker.withRegistry('https://index.docker.io/v1/', 'ct-dockerhub') {
                            sh '''
                                echo "$DOCKERHUB_PASSWORD" | docker login -u "$DOCKERHUB_USERNAME" --password-stdin
                                docker push "${IMAGE_NAME}:${IMAGE_TAG}"
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
                        docker run -d --restart on-failure --network host --name llmservice\
                        --env-file src/main/resources/.env ${IMAGE_NAME}:${IMAGE_TAG}
                    '''
                }
            }
        }
    }

    post {
        always {
            cleanWs()  // 빌드 후 작업 공간 정리
        }
    }
}