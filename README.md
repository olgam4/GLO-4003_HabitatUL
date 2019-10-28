[![Build Status](https://travis-ci.com/SamuelCabralCruz/GLO-4003.svg?token=BUYt3Vgakzzv3hSyH8Tn&branch=master)](https://travis-ci.com/SamuelCabralCruz/GLO-4003) [![codecov](https://codecov.io/gh/SamuelCabralCruz/GLO-4003/branch/master/graph/badge.svg?token=xVDhSPOnGU)](https://codecov.io/gh/SamuelCabralCruz/GLO-4003)

# Habitat UL

## Get Started

First thing first, in order to build the project and install all dependencies, simply run the following command:

```{bash}
mvn clean install
```

In order to start the project, simply run:

```{bash}
mvn exec:java -pl HabitatUL
```

If you want to run static style check analysis, you can use:

```{bash}
mvn checkstyle:checkstyle
```

To execute unit tests separately:

```{bash}
mvn surefire:test
```

However, integration and acceptance tests must be executed together:

```{bash}
mvn failsafe:integration-test
```

Otherwise, you can also run all validations with the simple command:

```{bash}
mvn verify
```
