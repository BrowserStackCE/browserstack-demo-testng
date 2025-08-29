pipeline {
	agent any
	tools {
		maven '3.9.11'
	}
	stages {
	    stage ('Initiate tests on BrowserStack') {
	        steps {
    			browserstack(credentialsId: "${credentials}") {
					sh "export BROWSERSTACK_RERUN=true"
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