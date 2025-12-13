pipeline {
    agent any

    environment {
        // Email pour les notifications
        RECIPIENTS = 'wadiasouiki@gmail.com'
        MAVEN_HOME = 'Maven_3.8'   // Nom de ton Maven installé dans Jenkins
        JDK_HOME = 'JDK_11'        // Nom de ton JDK installé dans Jenkins
    }

    tools {
        maven "${env.MAVEN_HOME}"
        jdk "${env.JDK_HOME}"
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

                // Génère le PDF depuis HTML
                bat '"C:\\Program Files\\wkhtmltopdf\\bin\\wkhtmltopdf.exe" --enable-local-file-access --javascript-delay 500 target\\site\\jacoco\\index.html target\\jacoco-report.pdf'

                // Petite pause pour s'assurer que le PDF est généré
                bat 'timeout /t 2'
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
            emailext(
                subject: "Build Jenkins: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "Le build Jenkins est terminé.\nStatut: ${currentBuild.currentResult}\nVoir les détails: ${env.BUILD_URL}",
                to: "${RECIPIENTS}",
                attachmentsPattern: "target/jacoco-report.pdf"
            )
        }
    }
}
