# Deployment

## 1.prepare nfs dir
> NOTE: Before running, need to create <YOUR-NFS-DATA-PATH>/pv-1g-{n} at <YOUR-NFS-SERVER>, otherwise, NFS will fail to mount with return code 32.

```bash
$$ pwd
YOUR-NFS-DATA-PATH
$$ sudo seq -f "YOUR-NFS-DATA-PATH/pv-1g-%01g" 1 22| xargs mkdir -p

```


## 2.deployment trainticket

```bash
$$ kubectl apply -f quickstart-ts-deployment-part1.yml

$$ kubectl apply -f quickstart-ts-deployment-part2.yml

$$ kubectl apply -f quickstart-ts-deployment-part3.yml
```
