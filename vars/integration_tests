def integration_tests(directory) {
 	dir(directory){	 
	   docker.image('circleci/node:12.13-browsers').inside{
		sh 'yarn install'
		sh 'yarn cypress install'
		// sh ' yarn cy:run'
		 }
	   }
}
