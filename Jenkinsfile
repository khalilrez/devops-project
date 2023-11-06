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
/*         stage("Test stage"){
            steps{
              script{
                sh "mvn test"
              }
            }
        } */
/*         stage("SonarTest integration"){
            steps{
                withSonarQubeEnv(installationName: 'SonarQubeServer') {
                    sh "mvn compile sonar:sonar"
                }
            }
        } */
/*         stage("Maven Package"){
            steps{
              script{
                sh "mvn clean package"
              }
            }
        } */
        /* stage('Push to Nexus') {
                    steps {
                        nexusArtifactUploader(
                            nexusVersion: 'nexus3',
                            protocol: 'http',
                            nexusUrl: 'localhost:8081',
                            groupId: 'tn.esprit.rh',
                            version: "1.0",
                            repository: 'achat-jar',
                            credentialsId: 'nexus-auth',
                            artifacts: [
                              [
                                artifactId: 'achat',
                                classifier: '',
                                file: "target/achat-1.0.jar",
                                type: 'jar'
                              ]
                            ]
                          )

                    }
                  } */
/*           stage("login & build docker"){
            steps {
              script{
                withCredentials([usernamePassword(credentialsId:'docker-auth', passwordVariable:'DOCKER_PASS', usernameVariable:'DOCKER_USER')]){
                  sh "echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin" //best practice: will not show password in the console...pswd will be stored in stdin
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
          } */
          stage("docker compose run"){
            steps{
              script{
                sh "docker-compose up -d"
              }
            }
          }
          stage("import jenkins metrics"){
            steps{
              script{
                sh '. /var/lib/jenkins/workspace/devops/prom.sh'
                sh "docker restart prometheus"
              }
            }
          }
    }
}