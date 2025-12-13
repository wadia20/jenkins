pipeline {
    agent any

    environment {
        RECIPIENTS = 'wadiasouiki@gmail.com'
        MAVEN_HOME = 'Maven_3.8'   // Nom de ton Maven dans Jenkins
        JDK_HOME   = 'JDK_11'      // Nom de ton JDK dans Jenkins
    }

    tools {
        maven "${MAVEN_HOME}"
        jdk "${JDK_HOME}"
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
                // Vérifie si le rapport HTML existe
                bat 'if not exist target\\site\\jacoco\\index.html exit 0'

                // Génère le PDF avec wkhtmltopdf
                bat '"C:\\Program Files\\wkhtmltopdf\\bin\\wkhtmltopdf.exe" --enable-local-file-access --javascript-delay 500 target\\site\\jacoco\\index.html target\\jacoco-report.pdf'
            }
        }
    }

    post {
        always {
            // Envoie l'email avec le PDF en pièce jointe
            emailext(
                subject: "Build Jenkins: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "Le build Jenkins est terminé.\nStatut: ${currentBuild.currentResult}\nVoir les détails: ${env.BUILD_URL}",
                to: "${RECIPIENTS}",
                attachmentsPattern: "target/jacoco-report.pdf"
            )
        }
    }
}
