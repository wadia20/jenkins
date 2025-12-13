pipeline {
    agent any

    environment {
        // Email pour les notifications
        RECIPIENTS = 'wadiasouiki@gmail.com'
        WKHTMLTOPDF = '"C:\\Program Files\\wkhtmltopdf\\bin\\wkhtmltopdf.exe"' // chemin vers wkhtmltopdf sur Windows
    }

    tools {
        // Assure-toi que ces noms correspondent aux installations Jenkins
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
                // Vérifie que le rapport HTML existe
                bat '''
                if not exist target\\site\\jacoco\\index.html exit 0
                %WKHTMLTOPDF% --enable-local-file-access --javascript-delay 500 target\\site\\jacoco\\index.html target\\jacoco-report.pdf
                '''
            }
        }
    }

    post {
        always {
            // Notifications par email avec PDF attaché
            emailext(
                to: "${RECIPIENTS}",
                subject: "Build Jenkins: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """Le build Jenkins est terminé.
Statut: ${currentBuild.currentResult}
Voir les détails: ${env.BUILD_URL}""",
                attachmentsPattern: "target/jacoco-report.pdf"
            )
        }
    }
}
