# infinispan-hotrod-client
An Infinispan HotRod Client with simple rest API and demonstrator

Download and start [Infinispan server.](https://infinispan.org/download/)

Build client application with:
```bash
mvn package
```

Start application in multiple machines with:
```bash
java -Dinfinispan.server.host=SERVER_IP -jar infinispan-client.jar
```

Or start multiple instances in single machine with different port numbers:
```bash
java -server.port=REST_API_PORT -Dinfinispan.server.host=SERVER_IP -jar infinispan-client.jar
```

Open multiple browser windows and go to:
```bash
http://IP_ADDRESS:REST_API_PORT/index.html
```

Subscribe to event endpoint at:
```bash
ws://IP_ADDRESS:REST_API_PORT/events
```

Create and modify some keys in registry and if everything goes well, all clients should get the updates.