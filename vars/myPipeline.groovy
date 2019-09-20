def myPipeline() {
    pipeline{
        agent any
           options {
              timestamps()
              timeout(time: 5, unit: 'MINUTES')
        }
        stages{
		stage("fetch from repository"){
		    steps{
		        git 'https://github.com/osec-jenkins/demoapp.git'
		    }
		}
		stage("build project"){
		    input {
		        message 'Shall we continue?'
		    }
		    steps{
		        sh "./mvnw package"
		        junit testResults: '**/surefire-reports/*.xml'
		        jacoco()
		    }
		}
        }
    }
}
