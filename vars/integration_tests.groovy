// def call(Map pipelineParams) {
//     withEnv([ 'CI = true', 'PUBLIC_URL = %PUBLIC_URL%']) {
//         docker.image('node:12').inside {
//             stage('tests') {
//                 //withCredentials([string(credentialsId: pipelineParams.npm_token, variable: 'NPMTOKEN')]) {
//                     stage('Install Dependencies') {
//                        // sh 'cd client; echo "//registry.npmjs.org/:_authToken=\${NPMTOKEN}" > .npmrc'
//                         sh 'cd client; yarn install'
//                     }
//                     stage('Lint') {
//                         sh 'cd client; yarn lint'
//                     }
//                     stage('Test') {
//                         sh 'cd client; yarn test'
//                     }
//                     stage('Build') {
//                         sh 'cd client; yarn build'
//                     }
//                 //}
//             }
//         }
//     }
// }




def call(Map pipelineParams) {
    docker.image('node:12').inside {
        stage('tests') {
            withCredentials([string(credentialsId: pipelineParams.token, variable: 'TOKEN')]) {
                echo '$TOKEN'
            }
        }
    }
}



// def call(Map pipelineParams) {
//   stage('tests') {
//      dir(pipelineParams.dir) {
//        docker.image('node:12').inside {
//          withCredentials([string(credentialsId: pipelineParams.token, variable: 'token')]) {
//             echo token
//          }
//         }
//      }
//   }
// }  






// def call(app_name,npm_token) {
//   // node {
//   stage('tests') {
// //     environment {
// //     NPM_TOKEN = credentials('npm-token')
// //     CI = 'true'
// //     PUBLIC_URL = '%PUBLIC_URL%'
// //    }
//      dir(app_name) {
//        docker.image('node:12').inside {
//          withCredentials([string(credentialsId: npm_token, usernameVariable: 'NPM_TOKEN')]) {
//             echo NPM_TOKEN
//              //echo app_name
//             // sh 'yarn install'
//          }
//         }
//      }
//   //}
// }     


// def call(directory) {
//    stages {
//      stage('test') {
//         steps {
//           echo directory
//         }
//      }
//    }
// }
