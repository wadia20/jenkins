pipeline {
    agent any

    environment {
        RECIPIENTS = 'wadiasouiki@gmail.com'
    }

    tools {
        maven 'Maven_3.8'
        jdk 'JDK_11'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build, Test & PDF') {
            steps {
                bat 'mvn clean test jacoco:report site site:pdf'
            }
        }
    }

    post {
        always {
            emailext(
                to: "${RECIPIENTS}",
                subject: "JaCoCo PDF Report - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                mimeType: 'text/html',
                body: """
                <h2>Rapport JaCoCo (PDF)</h2>
                <p><b>Statut :</b> ${currentBuild.currentResult}</p>
                <p><b>Build :</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                <p>Rapport JaCoCo PDF en pièce jointe</p>
                """,
                attachmentsPattern: 'target/jacoco-report.pdf'
            )
        }
    }
}
