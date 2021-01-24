# Widget store service

Simple spring-boot based service with REST api.

Service provide basic CRUD functionality for widgets managing. Use follow path for API
documentation: [/swagger-ui/index.html](localhost:8080/swagger-ui/index.html)



---

## Run service

Use scripts in ./scripts folder:

- **run.sh** - launches service locally
- **docker_build_run.sh** - build docker image and launches it
- **docker_run.sh** - pulls an image from Dockerhub and launches it
- **push.sh** - build docker image and push to Dockerhub

## Application settings

App provides several setting:

- storage.type - what type of storage system need to use. Available types:
    - **memory**: enable im memory storage
    - **postgres**: enable PostgreSQL database. NOTE!: needs to override database connection
- storage.algorithm - determines what in memory storage algorithm will be used
    - **linked**: see javadoc of LinkedStorage
    - **skiplist**: see javadoc of SkipListSetStorage
    - **withfactor**: see javadoc of WithFactorStorage

## Run local unit tests

In case you enable database - no need to create own instance of Postgres. It runs automatically by testcontainers.

```bash
mvn clean test -DtestFailureIgnore=true
```

---

## Some ideas for future improvements

- try to use [micronaut project](https://micronaut.io/) - for boost startup time and decrease memory usage
- add support to cloud technologies, such as service discovery, request tracing etc.
- use more effective libraries for data structures of inmemory storage system
- for more productivity we can split storage into several sections and deploy every shard on it own instance

## Features to add

- add paging support
- add filtering support

---
For any questions and ideas you may also write me [email](mailto:zuzyan@gmail.com)
