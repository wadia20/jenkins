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

        stage('Build, Tests & JaCoCo HTML') {
            steps {
                bat 'mvn clean test jacoco:report'
            }
        }

        stage('Generate JaCoCo PDF (Docker)') {
            steps {
                bat '''
                docker run --rm ^
                  -v "%WORKSPACE%\\target\\site\\jacoco:/data" ^
                  -v "%WORKSPACE%\\target:/output" ^
                  surnet/alpine-wkhtmltopdf:3.18.0-0.12.6-full ^
                  wkhtmltopdf ^
                  --enable-local-file-access ^
                  --load-error-handling ignore ^
                  --disable-external-links ^
                  /data/index.html /output/jacoco-report.pdf
                '''
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
                <p>Le rapport JaCoCo PDF est en pièce jointe.</p>
                """,
                attachmentsPattern: 'target/jacoco-report.pdf'
            )
        }
    }
}
