package practice;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.scr.AbstractCamelRunner;
import practice.internal.installRoute;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.spi.ComponentResolver;
import org.apache.felix.scr.annotations.*;

@Component(label = install.COMPONENT_LABEL, description = install.COMPONENT_DESCRIPTION, immediate = true, metatype = true)
@Properties({
    @Property(name = "camelContextId", value = "zomato"),
    @Property(name = "camelRouteId", value = "foo/timer-log"),
    @Property(name = "active", value = "true"),
    @Property(name = "from", value = "timer:foo?period=5000"),
    @Property(name = "to", value = "log:foo?showHeaders=true"),
    @Property(name = "messageOk", value = "Success: {{from}} -> {{to}}"),
    @Property(name = "messageError", value = "Failure: {{from}} -> {{to}}"),
    @Property(name = "maximumRedeliveries", value = "0"),
    @Property(name = "redeliveryDelay", value = "5000"),
    @Property(name = "backOffMultiplier", value = "2"),
    @Property(name = "maximumRedeliveryDelay", value = "60000")
})
@References({
    @Reference(name = "camelComponent",referenceInterface = ComponentResolver.class,
        cardinality = ReferenceCardinality.MANDATORY_MULTIPLE, policy = ReferencePolicy.DYNAMIC,
        policyOption = ReferencePolicyOption.GREEDY, bind = "gotCamelComponent", unbind = "lostCamelComponent")
})
public class install extends AbstractCamelRunner {

    public static final String COMPONENT_LABEL = "practice.install";
    public static final String COMPONENT_DESCRIPTION = "This is the description for zomato.";

    @Override
    protected List<RoutesBuilder>getRouteBuilders() {
        List<RoutesBuilder>routesBuilders = new ArrayList<>();
        routesBuilders.add(new installRoute(registry));
        return routesBuilders;
    }
}
