// def call(directory) {
//   stage('tests') {
//      dir(directory) {
//        echo directory
//      }
//   }
// }

def call(directory) {
   stages {
     stage('test') {
        steps {
          echo directory
        }
     }
   }
}
