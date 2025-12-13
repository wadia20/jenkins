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

        stage('Build') {
            steps {
                bat 'mvn clean install -DskipTests=false'
            }
        }

        stage('Run Tests') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Code Coverage') {
            steps {
                jacoco execPattern: 'target/jacoco.exec',
                       classPattern: 'target/classes',
                       sourcePattern: 'src/main/java'
            }
        }

        stage('Generate JaCoCo PDF') {
            steps {
                // Vérifie que le rapport existe
                bat 'if not exist target\\site\\jacoco\\index.html exit 0'

                // Génère le PDF à partir du rapport HTML
                bat '"C:\\Program Files\\wkhtmltopdf\\bin\\wkhtmltopdf.exe" --enable-local-file-access --javascript-delay 500 target\\site\\jacoco\\index.html target\\jacoco-report.pdf'

                // Optionnel : petit délai pour s'assurer que le PDF est écrit
                bat 'timeout /t 2 > nul'
            }
        }

        stage('Archive PDF') {
            steps {
                archiveArtifacts artifacts: 'target/jacoco-report.pdf', allowEmptyArchive: false
            }
        }
    }

    post {
        always {
            // Envoi email avec PDF attaché
            emailext(
                subject: "Build Jenkins: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "Le build Jenkins est terminé.\nStatut: ${currentBuild.currentResult}\nVoir les détails: ${env.BUILD_URL}",
                to: "${RECIPIENTS}",
                attachmentsPattern: "target/jacoco-report.pdf",
                replyTo: "wadiasouiki@gmail.com"
            )
        }
    }
}
