pipeline {
    agent any

    environment {
        // Remplace par ton email pour les notifications
        RECIPIENTS = 'wadiasouiki@gmail.com'
    }

    tools {
        // Assure-toi que ces noms correspondent aux installations Jenkins
        maven 'Maven_3.8' // ou change le nom si tu l’as configuré différemment
        jdk 'JDK_11'       // ou change le nom selon ta config Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install -DskipTests=false'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Code Coverage') {
            steps {
                jacoco execPattern: 'target/jacoco.exec',
                       classPattern: 'target/classes',
                       sourcePattern: 'src/main/java'
            }
        }
    }

    post {
        always {
            // Publier les résultats JUnit
            junit 'target/surefire-reports/*.xml'

            // Notifications par email
            mail to: "${RECIPIENTS}",
                 subject: "Build Jenkins: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: """Le build Jenkins est terminé.
Statut: ${currentBuild.currentResult}
Voir les détails: ${env.BUILD_URL}"""
        }
    }
}
