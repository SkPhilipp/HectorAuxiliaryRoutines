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

When you use it you'll either want to just copy paste the source to your project, if for some reason you want to use this project as a maven dependency you'll want to keep this in mind:

  public static final String CASSANDRA_HOST = System.getProperty("CASSANDRA_HOST", "localhost:9160");
    public static final String CASSANDRA_KEYSPACE = System.getProperty("CASSANDRA_KEYSPACE", "ExampleKeyspace");
    public static final int CASSANDRA_KEYSPACE_REPLICATION_FACTOR = Ints.tryParse(System.getProperty("CASSANDRA_KEYSPACE_REPLICATION_FACTOR", "1"));
    public static final String CASSANDRA_COLUMN_FAMILY_NAME = System.getProperty("CASSANDRA_COLUMN_FAMILY_NAME", "ExampleColumnFamily");

    public static final String HECTOR_CLUSTER_NAME = System.getProperty("HECTOR_CLUSTER_NAME", "ExamplePool");
    public static final String HECTOR_SCAN_CLASSPATH_PREFIX = System.getProperty("HECTOR_SCAN_CLASSPATH_PREFIX", "com.hileco.model");

Also keep in mind that `@Column` must have its `name` property set, otherwise Hector ORM will throw exceptions. Make sure to set it all up as in the test and your code should run smoothly.
