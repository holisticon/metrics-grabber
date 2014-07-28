# metrics-grabber

[![Build Status](https://secure.travis-ci.org/holisticon/metrics-grabber.png)](https://travis-ci.org/holisticon/metrics-grabber)

> Grabs metrics on JBoss AS 7+ (or EAP > 6.1.0 GA) via CLI and sends them to analyzing frameworks like graphite.

## There's JMX, why do we need grabbing metrics via the CLI Api?

We ran into the situation that querying JMX was very expensive in our environments (EAP 6.1.0 GA and EAP 6.2.0 GA).
A single jboss as related JMX query  allocated 5 MB up to 40MB memory (for example database connection or thread pool sizes).
Monitoring our application servers therefore lead to a significant CPU load because of triggered garbage collections.

Same queries done via the JBOSS CLI API were more or less cost neutral (they took about 2MB memory per query).
So using the CLI API helped us to minimize monitoring impact to your JBoss Appplication server.

Therefore the metrics grabber project offers a standalone agent and a embedded library that allows you grab metrics from inside a deployed WAR.

The agent works already very stable, so feel free to give it a try. 
The embedded libraries are still in development.

## Note : About querying non JBoss application server specific JMX beans

We didn't notice any performance implications while querying non JBoss application server specific metrics like offered by the JVM.

## Contributions

- (2014) Tobias Gindler (Holisticon AG)

## Sponsoring

This project is sponsored and supported by [holisticon AG](http://www.holisticon.de/)

# License

This project is released under the revised [BSD License](LICENSE).