#!groovy

// GIT config -------------------------------------------------------
def gitRepo = 'https://github.com/pradeep87giri/petStore.git'
def gitBranch = 'master'


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

        stage('Maven version') {
            steps {
                echo "Hello, Maven"
                sh "mvn --version"
            }
        }

        stage('Execute tests') {
            steps {
                echo 'Executing tests'
                sh "mvn clean test"
            }
        }

        stage('Publish report') {
            steps {
                publishHTML(target: [
                        allowMissing         : false,
                        alwaysLinkToLastBuild: true,
                        keepAll              : true,
                        reportDir            : 'test-output\\reports',
                        reportFiles          : 'extent-report.html',
                        reportName           : "Pet Store Report",
                        reportTitles         : 'Pet Store Report'])
            }
        }


        //Below step can be used to email the result
        stage("Emailing the result") {
            steps {
                echo 'Emailing status'
                emailext body: "Kindly find below the results of the automated tests executed. <BR>" +
                        "<BR><ul>Job: <strong>${JOB_NAME}</strong></ul>" +
                        "<ul>Build: <strong>${BUILD_NUMBER}</strong></ul>" +
                        "<ul>Status: <strong>" + currentBuild.currentResult + "</strong></ul><BR>" +
                        "<p>Check <strong>console</strong> output at ${BUILD_URL}console.<BR></p>" +
                        "Best regards,<BR><BR> Pradeep Giri",
                        subject: "${JOB_NAME} - Build # ${BUILD_NUMBER} - " + currentBuild.currentResult + "!",
                        recipientProviders: 'pradeep1987giri@gmail.com',
                        mimeType: "text/html"
            }
        }
    }
}
