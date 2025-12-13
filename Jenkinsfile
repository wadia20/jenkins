pipeline {
    agent any

    stages {
        stage('Build and Test') {
            steps {
                bat 'mvn clean test jacoco:report site'
            }
        }

        stage('Generate PDF Report') {
            steps {
                bat """
                "C:\\Program Files\\wkhtmltopdf\\bin\\wkhtmltopdf.exe" ^
                --enable-local-file-access ^
                --javascript-delay 500 ^
                target\\site\\jacoco\\index.html ^
                target\\jacoco-report.pdf
                """
            }
        }

        stage('Send Email') {
            steps {
                emailext(
                    subject: "JaCoCo Report",
                    body: "Veuillez trouver le rapport JaCoCo en pièce jointe.",
                    to: "wadiasouiki@gmail.com",
                    attachLog: false,
                    attachmentsPattern: "target/jacoco-report.pdf"
                )
            }
        }
    }
}
