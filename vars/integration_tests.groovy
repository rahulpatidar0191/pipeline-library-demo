def call(String name = 'directory') {
 	dir('Storefront/integration_tests/'){	 
	   docker.image('circleci/node:12.13-browsers').inside{
		sh 'yarn install'
		sh 'yarn cypress install'
		// sh ' yarn cy:run'
		 }
	   }
}
