def call(directory) {
 	dir(directory){	 
	   docker.image('circleci/node:12.13-browsers').inside{
		//sh 'yarn install'
		echo 'yarn cypress install'
		// sh ' yarn cy:run'
		 }
	   }
}
