package com.onlinehotel.hotelservice.adapter.config.grpc_eureka;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import io.grpc.*;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class EurekaNameResolver extends NameResolver {
    private final String serviceName;
    private final String portMetaData;
    private final EurekaClient eurekaClient;
    private Listener2 listener;

    public EurekaNameResolver(EurekaClient eurekaClient, String serviceName, String portMetaData) {
        this.serviceName = serviceName;
        this.portMetaData = portMetaData;
        this.eurekaClient = eurekaClient;
    }
    @Override
    public String getServiceAuthority() {
        return serviceName;
    }

    @Override
    public void start(Listener2 listener) {
        this.listener = listener;
        update();
    }

    private void update() {
        if (listener == null || eurekaClient == null) return;

        List<InstanceInfo> instances = eurekaClient.getInstancesByVipAddress(serviceName, false);
        List<EquivalentAddressGroup> addresses = new ArrayList<>();

        for (InstanceInfo instance : instances) {
            if (instance.getStatus() != InstanceInfo.InstanceStatus.UP) {
                System.out.println("Instance DOWN: " + instance.getHostName());
                continue;
            }

            String grpcPortStr = instance.getMetadata().get(portMetaData);
            System.out.println("Instance: " + instance.getHostName() +
                    ", default port=" + instance.getPort() +
                    ", grpc.port='" + grpcPortStr + "'");

            int port;
            if (grpcPortStr != null && !grpcPortStr.isEmpty()) {
                try {
                    port = Integer.parseInt(grpcPortStr);
                    System.out.println("✅ Using GRPC metadata port: " + port);
                } catch (NumberFormatException e) {
                    System.err.println("⚠️ Invalid grpc.port '" + grpcPortStr + "', fallback to default");
                    port = instance.getPort();
                }
            } else {
                System.err.println("❌ NO grpc.port metadata, using default HTTP port: " + instance.getPort());
                port = instance.getPort();
            }

            InetSocketAddress addr = new InetSocketAddress(instance.getHostName(), port);
            addresses.add(new EquivalentAddressGroup(addr));
        }

        ResolutionResult.Builder result = ResolutionResult.newBuilder();

        if (!addresses.isEmpty()) {
            result.setAddressesOrError(StatusOr.fromValue(addresses));
            listener.onResult(result.build());
        } else {
            result.setAddressesOrError(StatusOr.fromStatus(
                    Status.UNAVAILABLE.withDescription("No healthy Eureka instances for " + serviceName)
            ));
            listener.onResult(result.build());
        }
    }

    @Override
    public void shutdown() {
        listener = null;
    }
}
