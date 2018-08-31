package jahv.test;

import org.apache.camel.CamelContext;
import org.apache.camel.Consumer;
import org.apache.camel.Endpoint;
import org.apache.camel.EndpointConfiguration;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.PollingConsumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.builder.RouteBuilder;

import java.util.Map;

/**
 * A Camel Java DSL Router
 */
public class MyRouteBuilder extends RouteBuilder {

    /**
     * Let's configure the Camel routing rules using Java code...
     */
    public void configure() {

//        // here is a sample which processes the input files
//        // (leaving them in place - see the 'noop' flag)
//        // then performs content based routing on the message using XPath
//        from("file:src/data?noop=true")
//            .choice()
//                .when(xpath("/person/city = 'London'"))
//                    .log("UK message")
//                    .to("file:target/messages/uk")
//                .otherwise()
//                    .log("Other message")
//                    .to("file:target/messages/others");


        from("timer:simple?period=1000")
                .log("Sending message...")
                .setHeader("Header1", constant("HeaderValue"))
                .setBody(constant("Input Message"))
                .to("direct:processMessage")
                .end();

        from("direct:processMessage")
                .log("Init message processing")
                .log("Processing: ${body}, with header: ${header.Header1}")
                .end();
    }

}
