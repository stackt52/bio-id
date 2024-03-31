import com.github.gradle.node.npm.task.NpmTask

plugins {
    id("com.github.node-gradle.node") version "7.0.2"
}

node {
    download = true
    version = "21.6.2"
}

tasks.register("npmBuild", NpmTask::class.java) {
    dependsOn(tasks.npmInstall)
    group = "Custom"
    description = "building front-end app"
    workingDir = file("${project.projectDir}")
    args = listOf("run","build")
}