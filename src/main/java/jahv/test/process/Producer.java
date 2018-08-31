package jahv.test.process;

import jahv.test.pojos.Person;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class Producer implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getOut().setBody(new Person("JAHV", 29));
        exchange.getOut().setHeader("person", new Person("Julia", 0));

    }
}
