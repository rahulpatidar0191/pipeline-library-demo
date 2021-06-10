def client(Map pipelineParams) {

    node {
        stage('Checkout') {
            checkout scm
        }
        withEnv([ 'CI = true', 'PUBLIC_URL = %PUBLIC_URL%']) {
            docker.image('node:12').inside {
                stage('tests') {
                    withCredentials([string(credentialsId: pipelineParams.token, variable: 'NPM_TOKEN')]) {
                        stage('Install Dependencies') {
                            sh 'cd client; echo "//registry.npmjs.org/:_authToken=\${NPM_TOKEN}" > .npmrc'
                            sh 'cd client; yarn install'
                        }
                        stage('Lint') {
                            sh 'cd client; yarn lint'
                        }
                        stage('Test') {
                            sh 'cd client; yarn test'
                        }
                        stage('Build') {
                            sh 'cd client; yarn build'
                        }
                    }
                }
            }
        }
    }
}
