# WeatherWise

### Guide to run the project
- Import ice library (only required once):
  - File -> Project Structure -> Modules </br>
    -> Choose + in the right tab </br>
    -> Choose JARs or Directories </br>
    -> Add ice-3.7.2.jar and icestorm-3.7.2.jar from this repo

- Start Ice connection (only required once):
  - make sure bin folder and bin/db folder have these files:
    - [config.icebox](bin%2Fconfig.icebox)
    - [config.service](bin%2Fconfig.service)
    - [config.sub](bin%2Fconfig.sub)
    - [config.pub](bin%2Fconfig.pub)
  - open terminal
  - navigate to 'bin' folder
  - type this command: icebox --Ice.Config=config.icebox

- Go to src/main

- Run the components in the following order:
  LocationServer.java - PreferenceRepository.java - WeatherAlarms.java - ContextCoordinator.java -
  EnviroAPPUI.java