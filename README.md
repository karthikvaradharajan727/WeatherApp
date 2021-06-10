# WeatherApp
Technology:
  Programming Language: Kotlin
  Design Pattern: MVVM
  View Binding
  Dependency: Location service, retrofit for webservice

Classes:
  WeatherActivity(View):
    UI update from view model and permissions are handled
  WeatherViewModel(View Model):
    Location fetching, interacting with the repository and data layer
  WeatherRepository(Model):
    Fetching data from the webservice
    
Note:
Kindly check the Application with location On - I didn't handle in the code.
