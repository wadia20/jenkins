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

        stage('Build & Tests & Coverage') {
            steps {
                bat 'mvn clean test jacoco:report'
            }
        }
    }

    post {
        always {

            emailext(
                to: "${RECIPIENTS}",
                subject: "JaCoCo Report - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                <h2>Rapport JaCoCo</h2>
                <p><b>Statut :</b> ${currentBuild.currentResult}</p>
                <p><b>Build :</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                <p>Rapport JaCoCo en pièce jointe</p>
                """,
                mimeType: 'text/html',

                attachmentsPattern: 'target/site/jacoco/**/*.*'
            )
        }
    }
}
