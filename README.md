# Argela ONAP API

```                          _           ____  _   _          _____             _____ _____ 
     /\                  | |         / __ \| \ | |   /\   |  __ \      /\   |  __ \_   _|
    /  \   _ __ __ _  ___| | __ _   | |  | |  \| |  /  \  | |__) |    /  \  | |__) || |  
   / /\ \ | '__/ _` |/ _ \ |/ _` |  | |  | | . ` | / /\ \ |  ___/    / /\ \ |  ___/ | |  
  / ____ \| | | (_| |  __/ | (_| |  | |__| | |\  |/ ____ \| |       / ____ \| |    _| |_ 
 /_/    \_\_|  \__, |\___|_|\__,_|   \____/|_| \_/_/    \_\_|      /_/    \_\_|   |_____|
                __/ |                                                                    
               |___/                                                                     

Written By: Nebi Volkan UNLENEN ( unlenen@gmail.com ) 
```


This project aims to call ONAP complex APIs easily with converting them basic rest APIs.


## COMPILE
```
    mvn clean install
```
    

## RUN
```
    java -jar target/onap_service_manager-1.0.jar --onap.ip=<ONAP_IP> --server.port=8080
```

## USAGE Example
```
    curl http://localhost:8080/<API_URL>
    ex: get cloud regins : 
        curl http://localhost:8080/cloud/regions  | jq
```
  

## CLOUD

- List of Complexs: 
```
    /cloud/complexs
```
- List of Regions:
```
    /cloud/regions
```
- List of Tenants:
```
    /cloud/tenants/{cloudOwner}/{cloudRegion}
```
- List of Availability Zones
```
    /cloud/availability-zones/{cloudOwner}/{cloudRegion}
```

## BUSINESS

- List of Customers
```
    /business/customers
```
- List of Owning Entities
```
    /business/owning-entities
```
- List of Platforms
```
    /business/platforms
```
- List of Projects
```
    /business/projects
```

## DESIGN

- List of Vendors
```
    /design/vendors
```

- Create New Vendor
```
    /design/vendor/{vendorName}/{vendorDescription}
```

- Submit New Vendor
```
    /design/vendor-submit/{vendorId}/{vendorVersionId}
```

- List of VSPs
```
    /design/vsps
```
- List of VFs
```
    /design/vfs
```
- List of Service Models
```
    /design/service-models
```

## RUNTIME

- List of Service Instances
```
    /runtime/service-instances/{customerName}
```

- Detail of  Service Instance
```
    /runtime/service-instance/{serviceInstanceId}
```

- List of VNFS
```
    /runtime/vnfs
```

- Detail of VNF
```
    /runtime/vnf/{vnfId}
```

- List of VF-Modules
```
    /runtime/vf-modules/{vnfId}
```

- Detail of VF-Module
```
    /runtime/vf-module/{vnfId}/{vfModuleId}
```

- Instantiation Detail of VF-Module
```
    /runtime/vf-module-properties/{vfModuleId}
```

- VF-Module Topology
```
    /runtime/vf-module-topology/{serviceInstanceId}/{vnfId}/{vfModuleId}
```
