def call(directory) {
  stage('tests') {
     dir(directory) {
       echo directory
     }
  }
}

