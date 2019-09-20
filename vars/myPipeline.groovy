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
		stage("fail randomly"){
		    steps{
		        sh 'exit `shuf -i 0-1 -n 1`'
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
