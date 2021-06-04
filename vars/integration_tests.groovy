def call(directory) {
  dir(directory) {
    stages {
      stage('cypress parallel tests') {
        environment {
          // we will be recording test results and video on Cypress dashboard
          // to record we need to set an environment variable
          // we can load the record key variable from credentials store
          // see https://jenkins.io/doc/book/using/using-credentials/
          CYPRESS_RECORD_KEY = '5edfee75-c874-466c-b96c-3a457f8eca5c'
          // because parallel steps share the workspace they might race to delete
          // screenshots and videos folders. Tell Cypress not to delete these folders
          CYPRESS_trashAssetsBeforeRuns = 'false'
        }

        // https://jenkins.io/doc/book/pipeline/syntax/#parallel
        parallel {
          // start several test jobs in parallel, and they all
          // will use Cypress Dashboard to load balance any found spec files
          stage('tester A') {
            steps {
              dir('Storefront/') {
                sh 'yarn config set registry https://registry.yarnpkg.com/'
                sh 'yarn config set registry https://registry.npmjs.org/'
                sh 'yarn cache clean'
                sh 'yarn install --network-concurrency 1'
                sh 'yarn cypress install'
                echo "Running build ${env.BUILD_ID}"
              //sh 'yarn e2e:record:parallel'
              }
            }
          }

        // second tester runs the same command
        //         stage('tester B') {
        //           steps {
        //             dir('Storefront/') {
        //               sh 'yarn config set registry https://registry.yarnpkg.com/'
        //               sh 'yarn config set registry https://registry.npmjs.org/'
        //               sh 'yarn cache clean'
        //               sh 'yarn install --network-concurrency 1'
        //               sh 'yarn cypress install'
        //               echo "Running build ${env.BUILD_ID}"
        //               sh 'yarn e2e:record:parallel'
        //             }
        //           }
        //         }
        }
      }
    }
  }
}

