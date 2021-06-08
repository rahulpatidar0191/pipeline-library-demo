def name_one(directory) {
   node {
     stage('tests') {
        dir(directory) {
          echo directory
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
