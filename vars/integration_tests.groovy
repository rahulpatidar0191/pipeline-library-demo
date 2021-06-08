def call(app_name) {
  // node {
  stage('tests') {
    environment {
    NPM_TOKEN = credentials('npm-token')
    CI = 'true'
    PUBLIC_URL = '%PUBLIC_URL%'
   }
     dir(app_name) {
       docker.image('node:12').inside {
            echo NPM_TOKEN
             //echo app_name
            // sh 'yarn install'
         }
        }
     }
  //}
}     


// def call(directory) {
//    stages {
//      stage('test') {
//         steps {
//           echo directory
//         }
//      }
//    }
// }
