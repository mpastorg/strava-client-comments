pipeline {
  agent any
  environment {
    ENV = 'pre'
    RELEASE = '0.4'
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
      steps{
        sh "sed 's/MPGENV/pre/' strava-java-comments.yml > deplo_1.yml"
        sh "sed 's/MPGRELEASE.MPGBUILD_NUMBER/$RELEASE.$BUILD_NUMBER/' deplo_1.yml > deplo_2.yml"
      }
    }
    stage('SSH Into k8s Server') {
      steps{
        script{
            def remote = [:]
            remote.name = '192.168.25.22'
            remote.host = '192.168.25.22'
            remote.allowAnyHosts = true
            withCredentials([usernamePassword(credentialsId: 'mpgubumac01', usernameVariable: 'USERNAME'
            , passwordVariable: 'PASSWORD')])
            {
                remote.user = "$USERNAME"
                remote.password = "$PASSWORD"
                sshPut remote: remote, from: 'deplo_2.yml', into: '.'
                sshCommand remote: remote, command: "kubectl apply -f deplo_2.yml"
                sshRemove remote: remote, path: 'deplo_2.yml'
            }
        }
      }
    }
  }
}
