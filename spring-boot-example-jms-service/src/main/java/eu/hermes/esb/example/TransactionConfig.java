package eu.hermes.esb.example;

import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.JmsTransactionManager;

/**
 * Additional config for the JMS Transaction Manager
 * 
 * @author FinnernMal
 */
@Configuration
public class TransactionConfig {

  @Autowired
  private PooledConnectionFactory connectionFactory;

  @Bean
  public JmsTransactionManager jmsTransactionManager() {
    return new JmsTransactionManager(connectionFactory);
  }

}
