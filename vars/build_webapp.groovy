def server(Map pipelineParams) {    
   node {
      stage('Checkout'){
        checkout scm
      }

      stage('Build Docker') {
          echo 'Building docker image ...'
          def registry = pipelineParams.production_registry_host_satel
          env.REGISTRY = pipelineParams.production_registry_host_satel
          def credentials = pipelineParams.production_registry_creds_satel
          if (env.TAG_NAME != null) {
            registry = pipelineParams.production_registry_host_client
            env.REGISTRY = pipelineParams.production_registry_host_client
            credentials = pipelineParams.production_registry_creds_client
          }
          docker.withRegistry('https://' + registry, credentials) {

            env.CLEAN_BRANCH_NAME = BRANCH_NAME.replace('/', '_')
            def customImage = docker.build(registry + "/${pipelineParams.appname}:${CLEAN_BRANCH_NAME}", './')
            /* Push the container to DockerHub */
            customImage.push()
          }
      }
//       try {
//         stage('Docker up') {
//           //sh "docker-compose -f ${pipelineParams.dockerfiles[1]} -f ${pipelineParams.dockerfiles[2]} up -d"
//            echo "docker stack deploy --with-registry-auth -c ${pipelineParams.dockerfiles[3]} ${pipelineParams.appName}"
           
//         }
//         stage('Linting check') {
//           sh 'docker-compose exec -T webapp flake8'
//         }
//         stage('Reports clean up') {
//           sh 'rm -f unittesting.xml coverage.xml typing.xml'
//         }
//         stage('Code tests') {
//           sh 'jenkinsscripts/code-tests.sh'
//         }
//       }
//       catch (exc) {
//         echo 'The validation failed'
//         currentBuild.result = 'FAILURE'
//         if (BRANCH_NAME == 'main') {
//           slackSend channel: '#shape-pim', message: '@here The latest Jenkins job on the main branch of the Shape PIM FAILED.', teamDomain: 'satel', tokenCredentialId: 'slack-token', username: 'Jenkins'
//         }
//       }
//     }
}

// def server(Map pipelineParams) {
//     node {
//       stage('Checkout'){
//         checkout scm
//       }
//      stage('Build Docker') {
//          sh "docker-compose -f ${pipelineParams.dockerfiles[1]} -f ${pipelineParams.dockerfiles[2]} up -d"
//         // sh 'docker-compose -f ${pipelineParams.dockerfiles[1]} up -d'
//        // echo pipelineParams.dockerfiles[0]
//       }
//     }
// }

// def server(Map pipelineParams) {
//     node {
//       stage('Checkout'){
//         checkout scm
//       }

//       stage('Build Docker') {
//           echo 'Building docker image ...'
//           def registry = 'docker.satel.ca'
//           env.REGISTRY = 'docker.satel.ca'
//           def credentials = pipelineParams.credentials
//           echo credentials
//           if (env.TAG_NAME != null) {
//             registry = pipelineParams.registry
//             env.REGISTRY = pipelineParams.registry
//             credentials = pipelineParams.credentials1
           
//             }
//           // withCredentials([string(credentialsId: pipelineParams.credentials, variable: 'credentials')]) {
//                echo credentials
//               //docker.withRegistry('https://' + registry, credentials) {
//                 //env.CLEAN_BRANCH_NAME = BRANCH_NAME.replace('/', '_')
//                 //def customImage = docker.build(registry + "/pipelineParams.repo:${CLEAN_BRANCH_NAME}", './')
//                 /* Push the container to DockerHub */
//                 //customImage.push()
//              // }
//           // }
//       }
//     }
// }

