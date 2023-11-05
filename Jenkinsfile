#!/usr/bin/env groovy
pipeline{
    agent any
    tools{
        maven "MAVEN"
    }
    environment {
        IMAGE_NAME = 'yassinekh/devops'
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
          stage("login & build docker"){
            steps {
              script{
                withCredentials([usernamePassword(credentialsId:'docker-auth', passwordVariable:'DOCKER_PASS', usernameVariable:'DOCKER_USER')]){
                  sh "echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin"
                  sh "docker build -t ${IMAGE_NAME}:latest ."
                  sh "docker push ${IMAGE_NAME}:latest"
                }
              }
            }
          }
          stage("cleaning up"){
            steps{
              script{
                sh "docker image rm ${IMAGE_NAME}:latest"
              }
            }
          }
    }
}