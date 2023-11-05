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
                echo "sh mvn test"
              }
            }
        }

    }
}