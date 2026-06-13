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
        DOCKER_PASS = credentials('dockerhub')
        IMAGE_NAME  = "${DOCKER_USER}/${APP_NAME}"
        IMAGE_TAG   = "${RELEASE}-${BUILD_NUMBER}"
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
        sh 'echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin'
        
        sh 'docker build -t $IMAGE_NAME:$IMAGE_TAG -t $IMAGE_NAME:latest .'
        sh 'docker push $IMAGE_NAME:$IMAGE_TAG'
        sh 'docker push $IMAGE_NAME:latest'
        sh 'docker logout'
    }
    }
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
