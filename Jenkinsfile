#!/usr/bin/env groovy
pipeline{
    agent any
    tools{
        maven "MAVEN"
    }
    environment {
        IMAGE_NAME = 'gatrimohamedali/devops'
    }
    stages{
        stage("Test stage"){
            steps{
              script{
                sh "mvn test"
              }
            }
        }
        stage("SonarTest integration"){
            steps{
                withSonarQubeEnv(installationName: 'SonarQubeServer') {
                    sh "mvn compile sonar:sonar"
                }
            }
        }
        stage("Maven Package"){
            steps{
              script{
                sh "mvn clean package"
              }
            }
        }

    }
}