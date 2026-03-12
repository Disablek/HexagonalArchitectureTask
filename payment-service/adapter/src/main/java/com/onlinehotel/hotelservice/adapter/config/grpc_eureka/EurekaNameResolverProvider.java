package com.onlinehotel.hotelservice.adapter.config.grpc_eureka;

import com.netflix.discovery.EurekaClient;
import io.grpc.NameResolver;
import io.grpc.NameResolverProvider;
import lombok.Setter;

import java.net.URI;

public class EurekaNameResolverProvider extends NameResolverProvider {
    @Setter
    private static EurekaClient eurekaClient;

    private static String portMetaData ;

    public EurekaNameResolverProvider() {}

    @Override
    public String getDefaultScheme() { return "discovery"; }
    @Override protected boolean isAvailable() { return eurekaClient != null; }
    @Override protected int priority() { return 6; }

    public static void setPortMetaData(String meta) {
        portMetaData = meta;
        System.out.println("🔧 SET portMetaData = '" + portMetaData + "'");
    }

    @Override
    public NameResolver newNameResolver(URI targetUri, NameResolver.Args args) {
        String serviceName = targetUri.getPath().substring(1);
        System.out.println("🎯 newNameResolver: service=" + serviceName +
                ", portMetaData='" + portMetaData + "'" );

        return new EurekaNameResolver(eurekaClient, serviceName, portMetaData);
    }
}

