def server(Map pipelineParams) {    
   node {
      stage('Checkout'){
        checkout scm
      }

      stage('Build Docker') {
          echo 'Building docker image ...'
         sh '''
                 echo "Catch the exit codes so we don't exit the whole script before we are done."

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
   }

