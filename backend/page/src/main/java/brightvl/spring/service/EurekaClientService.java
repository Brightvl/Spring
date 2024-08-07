package brightvl.spring.service;


import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class EurekaClientService {

    private final DiscoveryClient discoveryClient;

    public EurekaClientService(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    public RestClient createRestClient(String serviceId) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        if (instances.isEmpty()) {
            throw new IllegalStateException("No instances available for service: " + serviceId);
        }
        int instanceIndex = ThreadLocalRandom.current().nextInt(0, instances.size());
        ServiceInstance instance = instances.get(instanceIndex);
        String uri = "http://" + instance.getHost() + ":" + instance.getPort();
        System.out.println("URI = " + uri);
        return RestClient.create(uri);
    }
}
