def server(Map pipelineParams) {    
   node {
      stage('Checkout'){
        checkout scm
      }

      stage('Build Docker') {
          echo 'Building docker image ...'
  
          }
      }
   }

