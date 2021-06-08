def call(app_name) {
  // node {
     stage('tests') {
        //dir(app_name) {
          echo app_name
       // }
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
