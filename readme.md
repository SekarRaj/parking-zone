# Parking Lot Implementation

Simple car parking automation effort built using Java. Gradle is used for building the application.

## Gradle tasks

```sh
$ ./gradlew clean build
```

```sh
$ gradlew test
```

## Test Setup

Spock framework is used for testing.


### Assumptions
* Single lot will be created; no support for multiple lot
* Single entrance and single exit (no concurrency)
* Max number of cars allowed is 100
* No payment related functionalities 
* No receipt functionalities 
* No space restrictions
* No storage functionality

* First lot is near the customer
