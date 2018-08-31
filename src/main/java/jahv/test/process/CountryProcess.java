package jahv.test.process;

import jahv.test.pojos.CountriesResponse;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class CountryProcess implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        CountriesResponse countriesResponse = exchange.getIn().getBody(CountriesResponse.class);

        if(countriesResponse != null) {
//            countriesResponse.getRestResponse().getResult().stream().forEach(c -> System.out.println(c.getName()));
            exchange.getOut().setBody(countriesResponse.getRestResponse().getResult());
//            exchange.getOut().setBody(null);
        }
        else{
            exchange.getOut().setBody(null);
        }
    }
}
