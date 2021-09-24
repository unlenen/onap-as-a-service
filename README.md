# ONAP API Basics

This project is targeting to call ONAP complex APIs easily with converting them basic rest APIs.

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
