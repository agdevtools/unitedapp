node {
    def gitRepository = 'https://github.com/agdevtools/unitedapp.git/'
    def gitBranch = '*/add-docker-test-container'
    def githubcreds = [
            $class      : 'UsernamePasswordMultiBinding',
            credentialsId : 'githubcreds',
            usernameVariable : 'GIT_USER',
            passwordVariable : 'GIT_PASS'
    ]

    stage('Clean Workspace') {
        cleanWs()
    }

    stage('Git Checkout') {
        withCredentials([githubcreds]){
            checkout([
                    $class      : 'GitSCM',
                    branches    : [[name:"${gitBranch}"]],
                    doGenerateSubModuleConfigurations : false,
                    extensions: [],
                    submoduleCfg: [],
                    userRemoteConfigs: [[credentialsId  : 'githubcreds',
                                         url            :"${gitRepository}"]]

            ])

        }
    }

    stage('Build') {

        sh './gradlew clean build -x test'

    }

    stage('Test') {

        sh './gradlew clean test --info'

    }

    stage('Deploy to Heroku') {

        sh './gradlew build deployHeroku'

    }
}