
def call() {    
   node {
      stage('Checkout'){
        checkout scm
      }
       stage('Code tests') {
          sh '''
                echo "Catch the exit codes so we don't exit the whole script before we are done."

               # Typing check
               docker-compose exec -T webapp mypy . --junit-xml typing.xml; STATUS1=$?

               docker cp "$(docker-compose ps -q webapp)":/python/app/typing.xml typing.xml

               # Unit and integration testing
               docker-compose exec -T webapp ./RunTest.sh; STATUS2=$?

               docker cp "$(docker-compose ps -q webapp)":/python/app/unittesting.xml unittesting.xml
               docker cp "$(docker-compose ps -q webapp)":/python/app/coverage.xml coverage.xml

               # Return the status code of mypy
               TOTAL=$((STATUS1 + STATUS2))

               exit $TOTAL
         '''
        }
   }












// def call() {    
//    node {
//       stage('Checkout'){
//         checkout scm
//       }


//       try {
//         stage('Docker up') {
//           //sh "docker-compose -f ${pipelineParams.dockerfiles[1]} -f ${pipelineParams.dockerfiles[2]} up -d"
//            //echo "docker stack deploy --with-registry-auth -c ${pipelineParams.dockerfiles[3]} ${pipelineParams.appName}"
           
//         stage('Code tests') {
//           sh '''
//                 # Catch the exit codes so we don't exit the whole script before we are done.

//                # Typing check
//                docker-compose exec -T webapp mypy . --junit-xml typing.xml; STATUS1=$?

//                docker cp "$(docker-compose ps -q webapp)":/python/app/typing.xml typing.xml

//                # Unit and integration testing
//                docker-compose exec -T webapp ./RunTest.sh; STATUS2=$?

//                docker cp "$(docker-compose ps -q webapp)":/python/app/unittesting.xml unittesting.xml
//                docker cp "$(docker-compose ps -q webapp)":/python/app/coverage.xml coverage.xml

//                # Return the status code of mypy
//                TOTAL=$((STATUS1 + STATUS2))

//                exit $TOTAL
//          '''
//         }
    //  }
//       catch (exc) {
//         echo 'The validation failed'
//         currentBuild.result = 'FAILURE'
//         if (BRANCH_NAME == 'main') {
//           slackSend channel: '#shape-pim', message: '@here The latest Jenkins job on the main branch of the Shape PIM FAILED.', teamDomain: 'satel', tokenCredentialId: 'slack-token', username: 'Jenkins'
//         }
   //   }
//     }
// }

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

*/
