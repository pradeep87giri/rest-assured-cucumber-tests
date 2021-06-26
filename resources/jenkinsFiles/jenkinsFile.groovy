#!groovy

// GIT config -------------------------------------------------------
def gitRepo = 'https://github.com/pradeep87giri/petStore.git'
def gitBranch = 'master'

// Pipeline config
def BUILD_STATUS
def emailID = 'pradeep87giri@gmail.com'


//Pipeline
pipeline {
    agent any
    stages {
        stage('Get the Git repo') {
            steps {
                echo 'Downloading the test automation code'
                git branch: gitBranch, url: gitRepo
            }
        }

        stage('Execute tests') {
            steps {
                echo 'Executing tests'
                sh "mvn clean test"
            }
        }

        stage("Emailing the result") {
            steps {
                BUILD_STATUS = currentBuild.currentResult
                echo 'Emailing report '
                emailext body: "Kindly find below the results of the automated tests executed. <BR>" +
                        "<BR><ul>Job: <strong>${JOB_NAME}</strong></ul>" +
                        "<ul>Build: <strong>${BUILD_NUMBER}</strong></ul>" +
                        "<ul>Status: <strong>${BUILD_STATUS}</strong></ul><BR>" +
                        "<p>Check <strong>console</strong> output at ${BUILD_URL}console.<BR></p>" +
                        "Best regards,<BR><BR> Pradeep Giri",
                        subject: "${JOB_NAME} - Build # ${BUILD_NUMBER} - ${BUILD_STATUS}!",
                        recipientProviders: emailID,
                        mimeType: "text/html"
            }
        }
    }
}
