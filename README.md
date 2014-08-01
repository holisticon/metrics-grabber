# metrics-grabber (Experimental - Work in Progress)

[![Build Status](https://secure.travis-ci.org/holisticon/metrics-grabber.png)](https://travis-ci.org/holisticon/metrics-grabber)

> Grabs metrics on JBoss AS 7+ (or EAP > 6.1.0 GA) via CLI and sends them to analyzing frameworks like graphite.

## There's JMX, why do we need grabbing metrics via the CLI API?

We ran into the situation that querying JMX was very expensive in our environments (EAP 6.1.0 GA and EAP 6.2.0 GA).
A single JBoss AS related JMX query (for example querying database connection or thread pool sizes) allocated up to 40MB memory. Querying a moderate amount of metrics lead to a significant CPU load because of triggered garbage collections.
Same queries done via the JBOSS CLI API were more or less cost neutral (they took about 2MB memory per query), therefore using the JBoss CLI API may help to minimize monitoring impact on your JBoss Appplication server.

On the other hand we didn't noticed any performance impact while querying non JBoss AS related JMX beans like jvm related beans.

The metrics grabber project offers a standalone agent and a embedded library that allows you grab metrics from inside a deployed WAR.

## Implications
- The CLI Api offered by JBoss isn't designed for long running applications like monitoring applications. Current and older versions of the JBoss CLI API seem to have a issues with memory leaks and are not very stable. For example : Clean up of bound resources is done at program shutdown, Finalizer are never called,... 
- You shouldn't use the embedded monitoring application at the moment because of possible memory leaks. Otherwise the monitoring webapp may kill your application server by eating up you memory. 

## Contributions

- (2014) Tobias Gindler (Holisticon AG)

## Sponsoring

This project is sponsored and supported by [holisticon AG](http://www.holisticon.de/)

# License

This project is released under the revised [BSD License](LICENSE).