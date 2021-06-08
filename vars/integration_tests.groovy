def call(app_name) {
  // node {
  stage('tests') {
     dir(app_name) {
       docker.image('node:12').inside {
             echo app_name
             sh 'yarn install'
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
