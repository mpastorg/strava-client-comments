pipeline {
  agent any
  environment {
    ENV = 'pre'
    RELEASE = '0.2'
    CONFIGMAP = '9946h98mtm'
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
    stage('prepare for kubernetes') {
        def filename = 'strava-java-comments.yml'
        sh "sed 's/MPGENV/pre/' strava-java-comments.yml > deplo_1.yml"
        sh "sed 's/MPGRELEASE.MPGBUILD_NUMBER/$RELEASE.$BUILD_NUMBER/' deplo_1.yml > deplo_2.yml"
    }
    stage('SSH Into k8s Server') {
            def remote = [:]
            remote.name = 'mpg4ras01'
            remote.host = 'mpg4ras01'
            remote.user = 'mpastorg'
            remote.password = 'marubuO1'
            remote.allowAnyHosts = true
    }
    stage('Put deplo.yml onto k8smaster') {
                sshPut remote: remote, from: 'deplo_2.yml', into: '.'
    }
    stage('Deploy to k8s') {
              sshCommand remote: remote, command: "kubectl apply -f deplo_2.yml"
     }
    stage('Remove Unused docker image') {
      steps{
        sh "docker rmi $registry:$RELEASE.$BUILD_NUMBER"
      }
    }
  }
}
