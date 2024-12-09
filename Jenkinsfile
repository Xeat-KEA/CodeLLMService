pipeline {
    agent any
    tools {
        gradle 'gradle'
    }

    environment {
        IMAGE_NAME = "hurraypersimmon/codingtext"  // Docker Hub ID와 리포지토리 이름
        IMAGE_TAG = "codellmservice"
        TARGET_HOST = "s119@172.16.211.119"
        SSH_CREDENTIALS = "jenkins_private_key"
        ACTIVE_PROFILE = "prod"
        CONFIG_SERVER_URL = "172.16.211.110:9000"
        SERVER_NAME = "s119"
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
                sh '''
                    echo 'gradlew 빌드 시작'
                    chmod +x ./gradlew
                    ./gradlew clean build 
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

        stage('Docker Image Deploy') {
            steps {
                // jenkins configure에서 SSH 설정을 통해 접속
                sshPublisher(publishers: [sshPublisherDesc(configName: '${SERVER_NAME}', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: '''
                    docker rm -f ${IMAGE_TAG} || true
                    docker image rm ${IMAGE_NAME}:${IMAGE_TAG} -f
                    docker run --name ${IMAGE_TAG} -d --network host --restart on-failure \
                                  --env ACTIVE_PROFILE=${ACTIVE_PROFILE}\
                                  --env CONFIG_SERVER_URL=${CONFIG_SERVER_URL}\
                                  ${IMAGE_NAME}:${IMAGE_TAG}
                    docker image prune -a -f''', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '', remoteDirectorySDF: false, removePrefix: '', sourceFiles: '')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
            }
        }
    }

    post {
        always {
            cleanWs()  // 빌드 후 작업 공간 정리
        }
    }
}