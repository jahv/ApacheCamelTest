package jahv.test.process;

import jahv.test.pojos.Person;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class Consumer implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        Person person = exchange.getIn().getBody(Person.class); //Tries to cast to person, if not possible returns null
        System.out.println("body: "+ person);

        person = exchange.getIn().getHeader("person", Person.class);
        System.out.println("header: "+ person);
    }

}
