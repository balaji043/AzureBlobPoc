package bam.balaji.poc;


import com.microsoft.azure.storage.StorageException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class AzureBlobController {

    private final AzureBlobService azureBlobService;

    public AzureBlobController(AzureBlobService azureBlobService) {
        this.azureBlobService = azureBlobService;
    }

    @PostMapping("/container/create")
    public ResponseEntity<Boolean> createContainer(@RequestBody String containerName) throws URISyntaxException, StorageException {
        boolean created = azureBlobService.createContainer(containerName);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/container/all")
    public ResponseEntity<List<String>> getAllBlobContainer() {
        List<String> uris = azureBlobService.listBlobContainer();
        return ResponseEntity.ok(uris);
    }

    @PostMapping("/blob/upload")
    public ResponseEntity<URI> upload(@RequestParam String containerName, @RequestParam MultipartFile multipartFile) throws StorageException, IOException, URISyntaxException {
        URI url = azureBlobService.upload(containerName, multipartFile);
        return ResponseEntity.ok(url);
    }

    @GetMapping("/blob/all")
    public ResponseEntity<List<URI>> getAllBlobs(@RequestParam String containerName) throws URISyntaxException, StorageException {
        List<URI> uris = azureBlobService.listBlobs(containerName);
        return ResponseEntity.ok(uris);
    }

    @DeleteMapping("/blob/delete")
    public ResponseEntity<Object> delete(@RequestParam String containerName, @RequestParam String blobName) throws URISyntaxException, StorageException {
        azureBlobService.deleteBlob(containerName, blobName);
        return ResponseEntity.ok().build();
    }
}
