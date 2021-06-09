// def call(Map pipelineParams) {
//     pipeline {
//       agent {
//         docker {
//           image 'node:12'
//         }
//       }
//       environment {
//         NPM_TOKEN = credentials('npm-token')
//         CI = 'true'
//         PUBLIC_URL = '%PUBLIC_URL%'
//       }
//       stages {
//         stage('Install Dependencies') {
//           steps {
//             /* groovylint-disable-next-line GStringExpressionWithinString */
//             sh 'cd client; echo "//registry.npmjs.org/:_authToken=\${NPM_TOKEN}" > .npmrc'
//             sh 'cd client; yarn install'
//           }
//         }
//         stage('Lint') {
//           steps {
//             sh 'cd client; yarn lint'
//           }
//         }
//         stage('Test') {
//           steps {
//             sh 'cd client; yarn test'
//           }
//         }
//         stage('Build') {
//           steps {
//             sh 'cd client; yarn build'
//           }
//         }
//       }
//     }
// }






def client(Map pipelineParams) {
    withEnv([ 'CI = true', 'PUBLIC_URL = %PUBLIC_URL%']) {
        docker.image('node:12').inside {
            stage('tests') {
                withCredentials([string(credentialsId: 'npm_token', variable: 'NPMTOKEN')]) {
                    stage('Install Dependencies') {
                        echo "$NPMTOKEN"
//                         sh 'cd client; echo "//registry.npmjs.org/:_authToken=\${NPMTOKEN}" > .npmrc'
//                         sh 'cd client; yarn install'
                    }
//                     stage('Lint') {
//                         sh 'cd client; yarn lint'
//                     }
//                     stage('Test') {
//                         sh 'cd client; yarn test'
//                     }
//                     stage('Build') {
//                         sh 'cd client; yarn build'
//                     }
                }
            }
        }
    }
}




// def call(Map pipelineParams) {
//     docker.image('node:12').inside {
//         stage('tests') {
//             withCredentials([string(credentialsId: pipelineParams.token, variable: 'TOKEN')]) {
//                 echo "Token: $TOKEN"
//             }
//         }
//     }
// }



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
