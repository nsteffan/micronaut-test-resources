package io.micronaut.testresources.kafka

import io.micronaut.context.ApplicationContext
import io.micronaut.context.annotation.Property
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject

@MicronautTest
@Property(name = KafkaTestResourceProvider.IMAGE_NAME_PROPERTY, value = "vectorized/redpanda:v21.11.3")
class CustomKafkaImageTest extends AbstractKafkaSpec {

    @Inject
    ApplicationContext applicationContext

    def "starts Kafka using a custom image"() {
        when:
        def client = applicationContext.getBean(AnalyticsClient)
        def result = client.updateAnalytics("oh yeah!")

        then:
        kafkaContainers().size() == 1
        result.block() == "oh yeah!"
    }

}
