package jahv.test;

import jahv.test.pojos.CountriesResponse;
import jahv.test.process.Consumer;
import jahv.test.process.CountryProcess;
import jahv.test.process.Producer;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.EndpointConfiguration;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.PollingConsumer;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;

import java.util.HashMap;
import java.util.Map;

/**
 * A Camel Java DSL Router
 */
public class MyRouteBuilder extends RouteBuilder {

    private JacksonDataFormat jacksonDataFormat;

    public MyRouteBuilder() {
        jacksonDataFormat = new JacksonDataFormat(CountriesResponse.class);
    }

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
//                .process(new Producer())
//                .to("direct:processMessage")
                .process(exchange -> {
                    exchange.getOut().setHeader("Country", "MX");
                })
                .to("direct:countriesWSRest")
                .end();

        from("direct:processMessage")
                .process(new Consumer())
                .end();

        from("direct:countriesWSRest")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .recipientList(simple("http://services.groupkt.com/country/get/iso2code/${header.Country}"))
//                .unmarshal(jacksonDataFormat)
//                .process(new CountryProcess())
                .log("${body}")
                .end();
    }

}
