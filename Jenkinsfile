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
                // Génère le rapport HTML
                bat 'mvn jacoco:report'
            }
        }

        stage('Zip Jacoco Report') {
            steps {
                // Zipper le dossier HTML généré
                bat 'powershell Compress-Archive -Path target/site/jacoco/* -DestinationPath target/jacoco-report.zip'
            }
        }
    }

    post {
        always {
            // Vérifie que le plugin Email Extension est installé
            emailext(
                to: "${RECIPIENTS}",
                subject: "Build Jenkins: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """Le build Jenkins est terminé.
    Statut: ${currentBuild.currentResult}
    Voir les détails: ${env.BUILD_URL}""",
                attachLog: false,
                attachmentsPattern: 'target/jacoco-report.zip'
            )
        }
    }

}
