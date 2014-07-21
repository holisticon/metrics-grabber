metrics-grabber
===============

Grabs metrics on JBoss AS 7+ via CLI and sends them to analyzing frameworks like graphite.

There's JMX, why do we need grabbing metrics via the CLI Api?
=============================================================

Because using JMX with the JBOSS AS 7+ is very very expensive. In our environments the Jboss AS allocate up to 40MB for a single JMX query (DB connection pool sizes).
Monitoring our application servers therefore lead to heavy CPU load because of triggered garbage collections.

Same queries done via the JBOSS CLI API was more or less cost neutral (About 1MB memory).
So using the CLI API helps you minimize monitoring impact to your JBoss Appplication server.

Therefore the metrics grabber project offers a standalone agent and a embedded library that allows you grab metrics from inside a deployed WAR.

The agent works already very stable, so feel free to give it a try. 
The embedded libraries are still in development.
