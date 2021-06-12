node {
    def gitRepository = 'https://github.com/agdevtools/unitedapp.git/'
    def gitBranch = '*/master'
    def githubcreds = [
            $class      : 'UsernamePasswordMultiBinding',
            credentialsId : 'githubcreds',
            usernameVariable : 'GIT_USER',
            passwordVariable : 'GIT_PASS'
    ]

    cleanWs()
    stage('Build') {
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
        sh './gradlew clean assemble'
    }
    stage('Test') {

        sh './gradlew clean test --info'

    }

    stage('Deploy to Heroku') {

        sh './gradlew build deployHeroku'

    }
}