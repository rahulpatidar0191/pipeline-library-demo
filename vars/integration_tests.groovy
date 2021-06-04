def call(directory) {
  dir(directory) {
             //sh 'yarn config set registry https://registry.yarnpkg.com/'
            // sh 'yarn config set registry https://registry.npmjs.org/'
            // sh 'yarn cache clean'
             sh 'yarn install --network-concurrency 1'
             sh 'yarn cypress install'
             echo "Running build ${env.BUILD_ID}"
              //sh 'yarn e2e:record:parallel'
  }
}

