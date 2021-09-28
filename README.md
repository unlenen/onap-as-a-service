# Argela ONAP API

```
                          _           ____  _   _          _____             _____ _____ 
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

## USAGE 
```
    curl http://localhost:8080/<API_URL>
    ex: get cloud regions : 
        curl http://localhost:8080/cloud/regions  | jq
```

## Swagger Rest UI
```
    http://localhost:8080/swagger-ui/
```  




## CLOUD

### Complex

- List of Complexs: 
```
    (GET)   /cloud/complexs
    curl -sX GET 'http://localhost:8080/cloud/complexs' | jq
```

### Region

- List of Regions:
```
    (GET)   /cloud/regions
    curl -sX GET 'http://localhost:8080/cloud/regions' | jq
```

- Add Openstack Cloud:
```
    (PUT)   /cloud/openstack/{name}/{cloudOwner}/{complexName}/{osDomain}/{osDefaultProject}?keystoneURL=&user=&password
    curl -X PUT "http://localhost:8080/cloud/openstack/<OS_NAME>/<CloudOwnerName>/<ComplexName>/<DOMAIN NAME>/<Default Project>>?keystoneURL=https%3A%2F%2Flocalhost%3A5000%2Fv3&password=<OPENSTACK PASSWORD>&user=<OPENSTACK USER>" -H  "accept: application/json"
```

### Tenant

- List of Tenants:
```
    (GET)   /cloud/tenants/{cloudOwner}/{cloudRegion}
    curl -sX GET 'http://localhost:8080/cloud/tenants/<CloudOwnerName>/<RegionName>' | jq
```

### Availability Zone

- List of Availability Zones
```
    (GET)   /cloud/availability-zones/{cloudOwner}/{cloudRegion}
    curl -sX GET 'http://localhost:8080/cloud/availability-zones/<CloudOwnerName>/<RegionName>' | jq
```

## BUSINESS

### Customer

- Create Customers
```
    (PUT)   /business/customer/{customerId}/{customerName}
    curl -sX PUT "http://localhost:8080/business/customer/<Customer ID>/<Customer Name>" | jq
```

- List of Customers
```
    (GET)   /business/customers
    curl -sX GET 'http://localhost:8080/business/customers' | jq
```

### Owning Entity

- List of Owning Entities
```
    (GET)   /business/owning-entities
    curl -sX GET 'http://localhost:8080/business/owning-entities' | jq
```

### Platforms

- List of Platforms
```
    (GET)   /business/platforms
    curl -sX GET 'http://localhost:8080/business/platforms' | jq
```

### Projects

- List of Projects
```
    (GET)   /business/projects
    curl -sX GET 'http://localhost:8080/business/projects' | jq
```

## DESIGN

### VENDOR

- List of Vendors
```
    (GET)   /design/vendors
    curl -sX GET 'http://localhost:8080/design/vendors' | jq
```

- Create New Vendor
```
    (PUT)   /design/vendor/{vendorName}/{vendorDescription}
     curl -sX PUT 'http://localhost:8080/design/vendor/<Vendor Name>/<Vendor Description>' | jq
```

- Submit New Vendor
```
    (PUT)   /design/vendor-submit/{vendorId}/{vendorVersionId}
    curl -sX PUT 'http://localhost:8080/design/vendor-submit/<Vendor ID>/<Vendor Version>' | jq
```

### Vendor Software Product (VSP)

- List of VSPs
```
    (GET)   /design/vsps
    curl -sX GET 'http://localhost:8080/design/vsps' | jq
```

- Create New VSP
```
    (PUT)   /design/vsp/{vendorId}/{vendorName}/{vspName}/{vspDescription}
    curl -sX PUT 'http://localhost:8080/design/vsp/<Vendor ID>/<Vendor Name>/<New VSP Name>/New VSP Description' | jq
```

- Get VSP Version
```
    (GET)   /design/vsp-versions/{vspId}
    curl -sX GET 'http://localhost:8080/design/vsp-versions/<VSP ID>' | jq
```

- Upload VSP File
```
    (PUT)   /design/vsp-file-upload/{vspId}/{vspVersionId}/{vspFileLocalPath}
    curl -sX PUT 'http://localhost:8080/design/vsp-file-upload/<VSP ID>/<VSP Version>/<VSP Artifact File Local Path>' | jq
```

- Process VSP File
```
    (PUT)   /design/vsp-file-process/{vspId}/{vspVersionId}
    curl -sX PUT 'http://localhost:8080/design/vsp-file-process/<VSP ID>/<VSP Version>' | jq
```

- Submit VSP
```
    (PUT)   /design/vsp-submit/{vspId}/{vspVersionId}
    curl -sX PUT 'http://localhost:8080/design/vsp-submit/<VSP ID>/<VSP Version>' | jq
```

- Create CSAR
```
    (PUT)   /design/vsp-csar/{vspId}/{vspVersionId}
    curl -sX PUT 'http://localhost:8080/design/vsp-csar/<VSP ID>/<VSP Version>' | jq
```

### Virtual Function (VF)

- List of VFs
```
    (GET)   /design/vfs
    curl -sX GET 'http://localhost:8080/design/vfs' | jq
```

- Create VFs
```
    (PUT)  /design/vf/{vendorName}/{vspId}/{vspVersionName}/{vfName}/{vfDescription}
    curl -sX PUT 'http://localhost:8080//design/vf/<Vendor Name>/<VSP ID>/<VSP Version Name(1.0)/<VF Name>/<VF Description>' | jq
```

### Service Model

- List of Service Models
```
    (GET)   /design/service-models
    curl -sX GET 'http://localhost:8080/design/service-models' | jq
```

## RUNTIME

### Service Instance

- List of Service Instances
```
    (GET)   /runtime/service-instances/{customerName}
    curl -sX GET 'http://localhost:8080/runtime/service-instances/<CUSTOMER NAME>' | jq
```

- Detail of  Service Instance
```
    (GET)   /runtime/service-instance/{serviceInstanceId}' | jq
    curl -sX GET 'http://localhost:8080/runtime/service-instance/<SERVICE INSTANCE ID>' | jq
```

### Virtual Network Function (VNF)

- List of VNFS
```
    (GET)   /runtime/vnfs
    curl -sX GET 'http://localhost:8080/runtime/vnfs' | jq
```

- Detail of VNF
```
    (GET)   /runtime/vnf/{vnfId}
    curl -sX GET 'http://localhost:8080/runtime/vnf/<VNF ID>' | jq
```

### Virtual Function Module (VF-Module)

- List of VF-Modules
```
    (GET)   /runtime/vf-modules/{vnfId}
    curl -sX GET 'http://localhost:8080/runtime/vf-modules/<VNF ID>' | jq
```

- Detail of VF-Module
```
    (GET)   /runtime/vf-module/{vnfId}/{vfModuleId}
    curl -sX GET 'http://localhost:8080/runtime/vf-module/<VNF ID>/<VNF MODULE ID>' | jq
```

- Instantiation Detail of VF-Module
```
    (GET)   /runtime/vf-module-properties/{vfModuleId}
    curl -sX GET 'http://localhost:8080//runtime/vf-module-properties/<VNF MODULE ID>' | jq
```

- VF-Module Topology
```
    (GET)   /runtime/vf-module-topology/{serviceInstanceId}/{vnfId}/{vfModuleId}
    curl -sX GET 'http://localhost:8080/runtime/vf-module-topology/<SERVICE INSTANCE ID>/<VNF ID>/<VF MODULE ID>' | jq
```
