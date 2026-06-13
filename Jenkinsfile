pipeline {
    agent { label 'agent-jenkins' }
    tools {
        jdk 'Java21'
        maven 'Maven3'
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
   }
}
