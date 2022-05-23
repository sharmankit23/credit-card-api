# Problem Statement:
	1. Create an API which creates credit cards in mongo db & fetches list of profiles created.
	2. Create mongo db using mongo docker image
	3. Create mongo express container.
	4. Creating credit_card_db in mongo db using mongo express
	

# Solution

### Creating Network
	$ docker network create credit-card-app-network
	
	$ docker network ls
	
### Creating a mongo DB container
	$ docker container run \
	--detach \
	--publish 27017:27017 \
	--name dbContainer \
	--env MONGO_INITDB_ROOT_USERNAME=rootuser \
	--env MONGO_INITDB_ROOT_PASSWORD=password \
	--network credit-card-app-network \
	mongo
	
	$ docker container ls

### Creating mongo express container
		
	$ docker container run \
	--detach \
	--name mongoExpContainer \
	--publish 8081:8081 \
	--restart always \
	--env ME_CONFIG_MONGODB_ADMINUSERNAME=rootuser \
	--env ME_CONFIG_MONGODB_ADMINPASSWORD=password \
	--env ME_CONFIG_MONGODB_SERVER=dbContainer \
	--network credit-card-app-network \
	mongo-express
	
	$ docker container ls

### Creating credit_card_db in mongo db using mongo express
	
	$ Open http://localhost:8081 & create credit_card_db
		
		
		