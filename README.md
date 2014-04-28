HectorAuxiliaryRoutines
=======================

Utilities for talking to Cassandra through Hector for when you've never used Cassandra or Hector.


The test describes how to use it, project is mavenized as well so you can just import it into your IDE. It's only one class under Java sources.

In case you missed it, here's the test:


        HectorAuxiliaryRoutines hectorAuxiliaryRoutines = HectorAuxiliaryRoutines.getInstance();
        hectorAuxiliaryRoutines.addColumnFamilyIfNotExists(Server.class.getSimpleName());
        EntityManagerImpl entityManager = hectorAuxiliaryRoutines.getEntityManager();

        try {

            // create something and save it
            Server server = new Server();
            server.setHostname("hello");
            server.setId(UUID.randomUUID());
            server.setIpAddress("127.0.0.1");
            server.setPort(80);
            entityManager.persist(server);
			
			      // get it back out
            Server server1 = entityManager.find(Server.class, server.getId());
            System.out.println("IpAddress = " + server1.getIpAddress());

			      // reaching this point is worth a celebration

        } finally {
            hectorAuxiliaryRoutines.getCluster().getConnectionManager().shutdown();
        }
