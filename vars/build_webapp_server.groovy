def call(Map pipelineParams) {

    node {
        stage('Checkout'){
            checkout scm
        }

        stage('Build Docker') {
            echo 'Building docker image ...'
            def registry = 'docker.satel.ca'
            env.REGISTRY = 'docker.satel.ca'
            def credentials = 'satel_bigbilly_registry'
            if (env.TAG_NAME != null) {
                registry = pipelineParams.production_registry_host_client
                env.REGISTRY = pipelineParams.production_registry_host_client
                credentials = pipelineParams.production_registry_creds_client
            }
            docker.withRegistry('https://' + registry, credentials) {
                env.CLEAN_BRANCH_NAME = BRANCH_NAME.replace('/', '_')
                def customImage = docker.build(registry + "/${pipelineParams.appName}:${CLEAN_BRANCH_NAME}", './')
            /* Push the container to DockerHub */
                customImage.push()
            }
        }
        try {
            stage('Docker up') {
                sh "docker-compose -f ${pipelineParams.dockerfiles[1]} -f ${pipelineParams.dockerfiles[2]} up -d"
            }
            stage('Linting check') {
                sh 'docker-compose exec -T webapp flake8'
            }
            stage('Reports clean up') {
                sh 'rm -f unittesting.xml coverage.xml typing.xml'
            }
            stage('Code tests') {
                sh 'jenkinsscripts/code-tests.sh'
            }
        }
        catch (exc) {
            echo 'The validation failed'
            currentBuild.result = 'FAILURE'
            if (BRANCH_NAME == 'main') {
                slackSend channel: "${pipelineParams.slack_channel}", message: "@here The latest Jenkins job on the main branch of the ${pipelineParams.appName} FAILED.", teamDomain: 'satel', tokenCredentialId: 'slack-token', username: 'Jenkins'
            }
        }
        finally {
                stage('Process reports') {
                    if (fileExists('typing.xml')) {
                        junit 'typing.xml'
                    }
                    if (fileExists('unittesting.xml')) {
                        junit 'unittesting.xml'
                    }
                    if (fileExists('coverage.xml')) {
                        step([$class: 'CoberturaPublisher', autoUpdateHealth: false, autoUpdateStability: false, coberturaReportFile: 'coverage.xml',
                    maxNumberOfBuilds: 0, onlyStable: false, sourceEncoding: 'ASCII', zoomCoverageChart: false])
                    }
                }
            // Make sure to always turn off the docker-compose
            stage('Docker down') {
                sh "docker-compose -f ${pipelineParams.dockerfiles[1]} -f ${pipelineParams.dockerfiles[2]} down"
            }
            if (BRANCH_NAME == 'main' && currentBuild.result != 'FAILURE') {
                stage('Deploy') {
                    echo "Deploy to  ${pipelineParams.appName}-qa.satelapps.com"
                    env.DOCKER_TLS_VERIFY = '1'
                    env.DOCKER_HOST = 'tcp://34.225.17.254:2376'
                    env.DOCKER_CERT_PATH = '/var/jenkins_home/.docker/machine/machines/satel-webapps-qa'
                    env.DOCKER_MACHINE_NAME = 'satel-webapps-qa'
                    withCredentials([usernamePassword(credentialsId: 'satel_bigbilly_registry', usernameVariable: 'DOCKERUSER', passwordVariable: 'DOCKERPASS')]) {
                        sh 'echo $DOCKERPASS | docker login -u satel --password-stdin docker.satel.ca'
                    }
                    sh "docker stack deploy --with-registry-auth -c ${pipelineParams.dockerfiles[3]} ${pipelineParams.appName}"
                }
            }
       }
    }
}
