package zm.gov.moh.apigateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class JAuthenticationFilter extends AbstractGatewayFilterFactory<JAuthenticationFilter.Config> {

    public JAuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {

            return chain.filter(exchange);
        }));
    }


    public static class Config {

    }
}
