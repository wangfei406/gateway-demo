```
#build package
./mvn package

#build docker image
docker build -t gateway-demo .

#run
docker run -p 9000:9000 gateway-demo

#test without header X-Request-ID1
curl http://localhost:9000/anything

#test with header X-Request-ID1
curl http://localhost:9000/anything -H "X-Request-ID1:123"
```
