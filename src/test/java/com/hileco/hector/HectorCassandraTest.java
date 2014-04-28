package com.hileco.hector;

import cmo.hileco.management.model.Server;
import com.hileco.management.HectorAuxiliaryRoutines;
import me.prettyprint.hom.EntityManagerImpl;
import org.junit.Test;

import java.util.UUID;

/**
 * Testing your setup of Cassandra with Hector.
 */
public class HectorCassandraTest {

    @Test
    public void test() {

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

    }

}
