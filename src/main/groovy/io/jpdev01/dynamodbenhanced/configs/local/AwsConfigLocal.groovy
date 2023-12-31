package io.jpdev01.dynamodbenhanced.configs.local

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient

@ConditionalOnProperty(value = 'application.localstack', havingValue = 'true')
@Configuration
class AwsConfigLocal {

    private static final URI URI_LOCALSTACK = URI.create('http://localhost:4566')
    private static final StaticCredentialsProvider STATIC_CREDENTIALS = StaticCredentialsProvider.create(AwsBasicCredentials.create('none', 'none'))

    @Bean
    DynamoDbClient dynamoDbClient() {
        DynamoDbClient.builder()
            .credentialsProvider(STATIC_CREDENTIALS)
            .endpointOverride(URI_LOCALSTACK)
            .region(Region.SA_EAST_1)
            .build()
    }


    @Bean
    DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient client) {
        DynamoDbEnhancedClient.builder().dynamoDbClient(client).build()
    }

}
