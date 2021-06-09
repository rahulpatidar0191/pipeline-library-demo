
def server(Map pipelineParams) {
    node {
      stage('Checkout'){
        checkout scm
      }

      stage('Build Docker') {
          echo 'Building docker image ...'
          def registry = 'docker.satel.ca'
          env.REGISTRY = 'docker.satel.ca'
          def credentials = 'satel_bigbilly_registry'
          if (env.TAG_NAME != null) {
            registry = pipelineParams.registry
            env.REGISTRY = pipelineParams.registry
            credentials = pipelineParams.credentials
              echo 'registry'
              echo 'credentials'
              
          }
//           docker.withRegistry('https://' + registry, credentials) {
//             env.CLEAN_BRANCH_NAME = BRANCH_NAME.replace('/', '_')
//             def customImage = docker.build(registry + "/pipelineParams.repo:${CLEAN_BRANCH_NAME}", './')
//               echo "Successsssss"
//             /* Push the container to DockerHub */
//             //customImage.push()
//           }
      }
    }
}

