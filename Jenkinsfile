pipeline {
    agent { label 'agent-jenkins' }
    tools {
        jdk 'Java21'
        maven 'Maven3'
    }
    environment {
        APP_NAME = "register-app"
        RELEASE = "1.0.0"
        DOCKER_USER = "joeljxhnson"
        IMAGE_NAME = "${DOCKER_USER}/${APP_NAME}"
        IMAGE_TAG = "${RELEASE}-${BUILD_NUMBER}"
    }
    stages{
        stage("Cleanup Workspace"){
            steps {
                cleanWs()
            }
        }
        
        stage("Github Cloning"){
            steps {
                git branch: 'main', credentialsId: 'github', url: 'https://github.com/jojohnoson/register-app-deployment'
            }
        }
        
        stage("Build Application"){
            steps {
                sh "mvn clean package"
            }
        }
        
        stage("Test Application"){
            steps {
                sh "mvn test"
            }
        }
        
        stage("Sonarqube Analysis"){
            steps{
                script{
                    withSonarQubeEnv(credentialId: 'jenkins-sonarqube-token') {
                        sh "mvn sonar:sonar"
                    }
                }
            }
        }
        
        stage("Quality Gate"){
            steps{
                script{
                    waitForQualityGate abortPipeline: false, credentialsId: 'jenkins-sonarqube-token'
                }
            }
        }
        
        stage("Build & Push Docker Image") {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh '''
                        echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                        docker build -t $IMAGE_NAME:$IMAGE_TAG -t $IMAGE_NAME:latest .
                        docker push $IMAGE_NAME:$IMAGE_TAG
                        docker push $IMAGE_NAME:latest
                        docker logout
                    '''
                }
            }
        }
        stage("Trivy Image Scan") {
             steps {
                script {
            sh '''
                docker run --rm -v /var/run/docker.sock:/var/run/docker.sock \
                aquasec/trivy:latest image \
                --no-progress \
                --scanner=vuln \
                --exit-code 0 \
                --severity HIGH,CRITICAL \
                --format table \
                ${IMAGE_NAME}:${IMAGE_TAG}
            '''
        }
    }
}
        stage("Cleanup Artifacts") {
            steps {
                script {
                   sh "docker rmi ${IMAGE_NAME}:${IMAGE_TAG}"
                   sh "docker rmi ${IMAGE_NAME}:latest"
                }
            }
        }
        stage("Test Docker Container") {
    steps {
        sh "docker run --rm -d -p 8081:8080 ${IMAGE_NAME}:${IMAGE_TAG}"
    }
}
       stage("Test Docker Container") {
    sh "docker run --rm -d -p 8081:8080 ${IMAGE_NAME}:${IMAGE_TAG}"
}
    }
    
    post{
        success{
            echo "The CI/CD pipeline has completed successfully."
        }
        failure{
            echo "The CI/CD pipeline has failed."
        }
    }
}