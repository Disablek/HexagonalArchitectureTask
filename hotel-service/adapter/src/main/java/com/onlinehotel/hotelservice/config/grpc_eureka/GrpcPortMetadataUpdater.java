package com.onlinehotel.hotelservice.config.grpc_eureka;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import org.springframework.context.event.EventListener;
import org.springframework.grpc.server.lifecycle.GrpcServerStartedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GrpcPortMetadataUpdater {

    private final ApplicationInfoManager applicationInfoManager;

    public GrpcPortMetadataUpdater(ApplicationInfoManager applicationInfoManager) {
        this.applicationInfoManager = applicationInfoManager;
    }

    @EventListener
    public void onGrpcServerStarted(GrpcServerStartedEvent event) {
        int actualPort = event.getServer().getPort();

        applicationInfoManager.registerAppMetadata(Map.of("grpc.port", String.valueOf(actualPort)));

        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.STARTING);
        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);

        System.out.println("gRPC port updated in Eureka metadata: " + actualPort);
    }
}
