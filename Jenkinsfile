pipeline {
	agent any
	tools {
		maven '3.9.11'
	}
	stages {
	    stage ('Pull GitHub Repository') {
	        steps {
    			git branch: 'jenkins', url: 'git@github.com:BrowserStackCE/browserstack-demo-testng.git'
	        }
		}
	    stage ('Initiate tests on BrowserStack') {
	        steps {
    			browserstack(credentialsId: "${credentials}") {
                    sh "mvn clean -P ${configuration} test"
                }
	        }
		}
	}
	post {
		always {
			junit 'target/surefire-reports/TEST-*.xml'
			browserStackReportPublisher "${product}"
		}
	}
}