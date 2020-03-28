package bam.balaji.poc;

import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AzureBlobService {

    private final CloudBlobClient cloudBlobClient;


    public AzureBlobService(CloudBlobClient cloudBlobClient) {
        this.cloudBlobClient = cloudBlobClient;
    }

    public boolean createContainer(String containerName) throws URISyntaxException, StorageException {
        boolean containerCreated;
        CloudBlobContainer container;
        container = cloudBlobClient.getContainerReference(containerName);
        containerCreated = container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(), new OperationContext());
        return containerCreated;
    }

    public URI upload(String containerName, MultipartFile multipartFile) throws URISyntaxException, StorageException, IOException {
        URI uri = null;
        CloudBlockBlob blob;
        CloudBlobContainer container = cloudBlobClient.getContainerReference(containerName);
        if (multipartFile.getOriginalFilename() != null) {
            blob = container.getBlockBlobReference(multipartFile.getOriginalFilename());
            blob.upload(multipartFile.getInputStream(), -1);
            uri = blob.getUri();
        }
        return uri;
    }

    public List<URI> listBlobs(String containerName) throws URISyntaxException, StorageException {
        List<URI> uris = new ArrayList<>();
        CloudBlobContainer container = cloudBlobClient.getContainerReference(containerName);
        for (ListBlobItem blobItem : container.listBlobs()) {
            uris.add(blobItem.getUri());
        }
        return uris;
    }

    public void deleteBlob(String containerName, String blobName) throws URISyntaxException, StorageException {
        CloudBlobContainer container = cloudBlobClient.getContainerReference(containerName);
        CloudBlockBlob blobToBeDeleted = container.getBlockBlobReference(blobName);
        blobToBeDeleted.deleteIfExists();
    }

    public List<String> listBlobContainer() {
        List<String> blobContainerNames = new ArrayList<>();
        for (CloudBlobContainer cloudBlobContainer : cloudBlobClient.listContainers()) {
            blobContainerNames.add(cloudBlobContainer.getName());
        }
        return blobContainerNames;
    }
}
