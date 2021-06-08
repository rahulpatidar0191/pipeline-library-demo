def call(app_name) {
   node {
     stage('tests') {
        dir(directory) {
          echo app_name
        }
     }
  }
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
