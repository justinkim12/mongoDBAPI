package api.mongo.nosql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableMongoRepositories("api.mongo.nosql.Domain.mongoRepository")
@EnableNeo4jRepositories("api.mongo.nosql.Domain.neo4jRepository")
public class NosqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(NosqlApplication.class, args);
	}

}
