def integration_tests(String name = 'directory') {
 	dir(${name}){	 
	   docker.image('circleci/node:12.13-browsers').inside{
		sh 'yarn install'
		sh 'yarn cypress install'
		// sh ' yarn cy:run'
		 }
	   }
}
