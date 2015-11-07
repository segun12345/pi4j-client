import com.typesafe.sbt.SbtMultiJvm.MultiJvmKeys

lazy val root = Project("root", file("."))
  .aggregate(core, api, consoleClient, remoteClient, remoteServer, examples)
  .settings(Publish.noPublishing: _*)

lazy val api = Project("api", file("api"))
  .settings(BaseSettings.defaultSettings: _*)
  .settings(Dependencies.api: _*)
  .settings(Testing.defaultSettings: _*)
  .settings(Coverage.apiSettings: _*)

lazy val core = Project("core", file("core"))
  .dependsOn(api)
  .settings(BaseSettings.debugSettings: _*)
  .settings(Dependencies.core: _*)
  .settings(Testing.coreSettings: _*)
  .settings(Coverage.coreSettings: _*)

lazy val consoleClient = Project("console-client", file("console-client"))
  .dependsOn(core)
  .settings(BaseSettings.defaultSettings: _*)
  .settings(Dependencies.console: _*)
  .settings(Testing.defaultSettings: _*)

lazy val webClient = Project("web-client", file("web-client"))
  .dependsOn(core)
  .settings(BaseSettings.defaultSettings: _*)
  .settings(Dependencies.web: _*)
  .settings(Testing.defaultSettings: _*)
  .settings(Publish.noPublishing: _*)

lazy val mockClient = Project("mock-client", file("mock-client"))
  .dependsOn(core)
  .settings(BaseSettings.defaultSettings: _*)
  .settings(Dependencies.mock: _*)
  .settings(Testing.defaultSettings: _*)
  .settings(Publish.noPublishing: _*)

lazy val remoteClient = Project("remote-client", file("remote-client"))
  .dependsOn(core)
  .settings(BaseSettings.defaultSettings: _*)
  .settings(Dependencies.remoteClient: _*)
  .settings(Testing.multiJmvSettings: _*)
  .configs(MultiJvmKeys.MultiJvm)

lazy val remoteServer = Project("remote-server", file("remote-server"))
  .dependsOn(api)
  .settings(BaseSettings.debugSettings: _*)
  .settings(Dependencies.re

    moteServer: _*)
  .settings(Testing.defaultSettings: _*)
  .settings(Assembly.remoteServerSettings: _*)

lazy val examples = Project("examples", file("examples"))
  .dependsOn(core, consoleClient, webClient, mockClient, remoteClient)
  .settings(BaseSettings.exampleSettings: _*)
  .settings(Coverage.exampleSettings: _*)
  .settings(Publish.noPublishing: _*)

