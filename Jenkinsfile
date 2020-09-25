pipeline {
  agent any
  environment {
    RELEASE = '0.1.pre'
    registry = "mpastorg/client-comments"
    registryCredential = 'dockerhub'
    dockerImage = ''
  }
  stages {
    stage('Build image') {
        /* This builds the actual image; synonymous to
         * docker build on the command line */
      steps {
        script {
          dockerImage = docker.build("$registry:$RELEASE.$BUILD_NUMBER")
        }
      }
    }
    stage('Deploy Image') {
      steps{
        script {
          docker.withRegistry( '', registryCredential ) {
            dockerImage.push()
          }
        }
      }
    }
    stage('Deploy on kubernetes') {
      steps {
          kubernetesDeploy(
              kubeconfigId: '1c99e9a6-3159-41f1-a7d3-2e4dbbe13455',
              configs: 'strava-java-comments-pre.yml',
              enableConfigSubstitution: true
          )
      }
    }
    stage('Remove Unused docker image') {
      steps{
        sh "docker rmi $registry:$RELEASE.$BUILD_NUMBER"
      }
    }
  }
}
