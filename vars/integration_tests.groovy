def call(directory) {
 	dir(directory){	 
	   docker.image('circleci/node:12.13-browsers').inside{
		sh 'yarn install'
		//sh 'yarn cy:run --record --key 5edfee75-c874-466c-b96c-3a457f8eca5c'
		sh 'yarn e2e:record:parallel'   
		 }
	   }
}
