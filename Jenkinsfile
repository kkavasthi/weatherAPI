pipeline {
  agent any
  tools{
    maven 'maven 3.8.1'
  }

  stages {
    stage ('Checkout code') {
      steps {
        checkout scm
      }
    }
    stage ('Build Aftifact') {
      steps {
        sh 'mvn install'
      }
    }

    stage('Docker Build') {
      steps {
        script{
          env.DOCKER_IMAGE="${env.DOCKER_ARTIFACTORY}:${env.APP_VERSION}"
          sh "docker build -t weatherapi-dockerfile ."
        }
      }
    }
    stage('Push to DockerHub') {
      steps {
        withCredentials([string(credentialsId: 'docherHub-Pwd', variable: 'docherHubUserPwd')]) {
          sh "docker login -u kkavasthi -p ${docherHubUserPwd}"
          sh "docker push weatherapi-dockerfile"
        }
      }
    }

    stage('Deploy') {
      steps {
        sh kubernetesDeploy{configs: 'deployment.yaml',kubeconfigId:'k8sconfigpwd'}
      }
    }
  }
}