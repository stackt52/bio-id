# Bio Data Service

The `Bio Data Service` manages fingerprint data for all enrolled subjects.
The service exposes endpoints for persisting, reading, updating and deleting
the fingerprint data.

This service uses a [Redis](https://redis.io/) datastore for persistence.

## Redis Setup - Development
To start a Redis Stack container using the `redis-stack` image, run the following command in your terminal:
```shell
docker run -d --name redis-stack -p 6379:6379 -p 8001:8001 redis/redis-stack:latest
```

The command above starts `Redis` on port `6379`. You can use this to configure the `port` number
of to your redis server in the `properties/config/bio-data-service.yaml` file within the `config-server` service. You 
can set the `hostname` property to `localhost` if docker is running locally on your machine.


The docker run command above also exposes RedisInsight on port 8001. You can use RedisInsight by pointing 
your browser to [localhost:8001](http://localhost:8001).

### Connect with redis-cli

You can then connect to the server using redis-cli, just as you connect to any Redis instance.
If you don’t have redis-cli installed locally, you can run it from the Docker container:

```shell
docker exec -it redis-stack redis-cli
```

### Persistence Configuration
To mount directories or files to your Docker container, specify -v to configure a local volume. This command stores all data in the local directory local-data:

```shell
docker run -v /local-data/:/data redis/redis-stack:latest
```

## Redis Setup - Production
The `docker-compose` file specifies appropriate configuration field for `Redis` production-ready deployment.