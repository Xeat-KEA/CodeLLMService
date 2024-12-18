pipeline {
    agent any
    tools {
        gradle 'gradle'
    }


    environment {
        IMAGE_NAME = "hurraypersimmon/codingtext"  // Docker Hub ID와 리포지토리 이름
        IMAGE_TAG = "codellmservice"
        ACTIVE_PROFILE = "prod"
        CONFIG_SERVER_URL = "172.16.211.110:9000"
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
                    cd /var/lib/jenkins/workspace/llm-service
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

        stage('Docker Image Deploy to s119') {
            steps {
                // jenkins config에서 설정한 SSH password를 사용하여 원격 호스트에 접속
                sshPublisher(publishers: [sshPublisherDesc(configName: 's119', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: '''
                    docker rm -f codellmservice
                    docker system prune -a -f  
                    docker run --name codellmservice -d --network host --restart on-failure\
                                --env ACTIVE_PROFILE=prod\
                                --env CONFIG_SERVER_URL=172.16.211.110:9000\
                                hurraypersimmon/codingtext:codellmservice\\
                                java -Dnetworkaddress.cache.ttl=0 -Dnetworkaddress.cache.negative.ttl=0 -jar /app.jar
                    ''', execTimeout: 120000,flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '', remoteDirectorySDF: false, removePrefix: '', sourceFiles: '')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
            }
        }

        stage('Docker Image Deployto s118') {
            steps {
                // jenkins config에서 설정한 SSH password를 사용하여 원격 호스트에 접속
                sshPublisher(publishers: [sshPublisherDesc(configName: 's118', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: '''
                    docker rm -f codellmservice
                    docker system prune -a -f  
                    docker run --name codellmservice -d --network host --restart on-failure\
                                --env ACTIVE_PROFILE=prod\
                                --env CONFIG_SERVER_URL=172.16.211.110:9000\
                                hurraypersimmon/codingtext:codellmservice\\
                                java -Dnetworkaddress.cache.ttl=0 -Dnetworkaddress.cache.negative.ttl=0 -jar /app.jar
                    ''', execTimeout: 120000,flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '', remoteDirectorySDF: false, removePrefix: '', sourceFiles: '')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
            }
        }
    }

    post {
        always {
            sh "docker system prune -a -f"
            cleanWs()  // 빌드 후 작업 공간 정리
        }
    }
}
